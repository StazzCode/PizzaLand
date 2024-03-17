package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CommandeDAODatabase;
import dao.DAOCommande;
import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.Commande;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandeRestAPI extends MyServlet {
    DAOCommande dao = new CommandeDAODatabase();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Commande> list = dao.findAll();
            out.println(obj.writeValueAsString(list));
            return;
        }
        String[] splits = info.split("/");
        if (splits.length < 2 || splits.length > 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int id = Integer.parseInt(splits[1]);
            Commande c = dao.findById(id);
            if (c == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (splits.length == 2) {
                out.println(obj.writeValueAsString(c));
            } else if (splits[2].equals("name")) {
                out.println(obj.writeValueAsString(c.getNom()));
            } else if (splits[2].equals("prixfinal")) {
                out.println(obj.writeValueAsString(c.getPrixBase()));
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            out.println(e.getMessage());
        }
        return;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (verifToken(req, res)) {

            res.setContentType("application/json;charset=UTF-8");
            ObjectMapper obj = new ObjectMapper();
            PrintWriter out = res.getWriter();
            BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String data = bf.readLine();
            String info = req.getPathInfo();
            if (info == null || info.equals("/")) {
                Commande i = obj.readValue(data, Commande.class);
                if (!dao.isValid(i)) {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                if (dao.findById(i.getId()) != null) {
                    res.sendError(HttpServletResponse.SC_CONFLICT);
                    return;
                }
                dao.save(i);
                out.println(data);
                return;
            }
            String[] splits = info.split("/");
            if (splits.length != 2) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            try {
                int id = Integer.parseInt(splits[1]);
                Commande c = dao.findById(id);
                Pizza i = obj.readValue(data, Pizza.class);
                DAOPizza daoPizza = new PizzaDAODatabase();
                if (c == null || i == null) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if (!daoPizza.isInDatabase(i)) {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                dao.addPizza(c, i);
                out.println(obj.writeValueAsString(c));

            } catch (Exception e) {
                out.println(e.getMessage());
            }
            bf.close();
            return;
        }
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (verifToken(req, res)) {

            res.setContentType("application/json;charset=UTF-8");
            ObjectMapper obj = new ObjectMapper();
            PrintWriter out = res.getWriter();
            String info = req.getPathInfo();

            if (info == null || info.equals("/")) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] splits = info.split("/");
            if (splits.length != 2) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(splits[1]);
            Commande c = dao.findById(id);
            if (c == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));
                String data = bf.readLine();
                Commande updatedCommande = obj.readValue(data, Commande.class);

                if (updatedCommande.getNom() != null) {
                    c.setNom(updatedCommande.getNom());
                }
                if (updatedCommande.getDate() != null) {
                    c.setDate(updatedCommande.getDate());
                }
                if (updatedCommande.getPizzas() != null) {
                    c.setPizzas(updatedCommande.getPizzas());
                }

                dao.update(c);
                out.println(obj.writeValueAsString(c));
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (verifToken(req, res)) {

            res.setContentType("application/json;charset=UTF-8");
            String info = req.getPathInfo();

            if (info == null || info.equals("/")) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] splits = info.split("/");
            if (splits.length > 3) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(splits[1]);
            Commande c = dao.findById(id);
            if (c == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (splits.length == 3) {
                DAOPizza daoPizza = new PizzaDAODatabase();
                int idPizza = Integer.parseInt(splits[2]);
                Pizza p = daoPizza.findById(idPizza);
                if (!c.getPizzas().contains(p)) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                dao.deletePizza(c, p);
                res.sendError(HttpServletResponse.SC_NO_CONTENT);
                return;
            } else {
                dao.remove(id);
            }
        }
    }
}
