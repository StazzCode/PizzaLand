package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.Ingredient;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends HttpServlet{
    DAOPizza dao = new PizzaDAODatabase();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        if(info == null || info.equals("/")){
            Collection<Pizza> list = dao.findAll();
            out.println(obj.writeValueAsString(list));
            return;
        }
        String[] splits = info.split("/");
        if(splits.length < 2 || splits.length > 3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try{
            int id = Integer.parseInt(splits[1]);
            Pizza p = dao.findById(id);
            if(p == null){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if(splits.length == 2){
                out.println(obj.writeValueAsString(p));
            }else if(splits[2].equals("name")){
                out.println(obj.writeValueAsString(p.getNom()));
            }else{
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch(NumberFormatException e){
            out.println(e.getMessage());
        }
        return;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String data = new BufferedReader(new InputStreamReader(req.getInputStream())).readLine();
        
        Pizza i = obj.readValue(data, Pizza.class);
        if(dao.findById(i.getId()) != null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        dao.save(i);
        out.println(data);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();

        if(info == null || info.equals("/")){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = info.split("/");
        if(splits.length!=2){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(splits[1]);
        Pizza p = dao.findById(id);
        if(p == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        dao.remove(id);
        res.sendError(HttpServletResponse.SC_NO_CONTENT);
    }
}
