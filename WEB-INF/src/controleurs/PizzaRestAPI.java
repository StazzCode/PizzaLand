package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOIngredient;
import dao.DAOPizza;
import dao.IngredientDAODatabase;
import dao.PizzaDAODatabase;
import dto.Ingredient;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends MyServlet{
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
            }else if(splits[2].equals("prixfinal")){
                out.println(obj.writeValueAsString(p.getPrixBase()));
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
        BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String data = bf.readLine();
        String info = req.getPathInfo();
        if(info == null || info.equals("/")){
            Pizza i = obj.readValue(data, Pizza.class);
            if(!dao.isValid(i)){
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if(dao.findById(i.getId()) != null){
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            dao.save(i);
            out.println(data);
            return;
        }
        String[] splits = info.split("/");
        if(splits.length != 2){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try{
            int id = Integer.parseInt(splits[1]);
            Pizza p = dao.findById(id);
            Ingredient i = obj.readValue(data, Ingredient.class);
            if(p == null || i == null){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            dao.addIngredients(p, i);
            out.println(obj.writeValueAsString(p));

        }catch(Exception e){
            out.println(e.getMessage());
        }
        bf.close();
        return;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        String info = req.getPathInfo();

        if(info == null || info.equals("/")){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = info.split("/");
        if(splits.length>3){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(splits[1]);
        Pizza p = dao.findById(id);
        if(p == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        if (splits.length==3){
            DAOIngredient daoIngredients = new IngredientDAODatabase();
            int idIngredient = Integer.parseInt(splits[2]);
            Ingredient ing = daoIngredients.findById(idIngredient);
            if (!p.getIngredients().contains(ing)){
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            dao.deleteIngredient(p, ing);
        } else {
            dao.remove(id);
        }
        res.sendError(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        Pizza p = dao.findById(id);
        if (p == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String data = bf.readLine();
            Pizza updatedPizza = obj.readValue(data, Pizza.class);

            if (updatedPizza.getNom() != null) {
                p.setNom(updatedPizza.getNom());
            }
            if (updatedPizza.getIngredients() != null) {
                p.setIngredients(updatedPizza.getIngredients());
            }
            if (updatedPizza.getPate() != null) {
                p.setPate(updatedPizza.getPate());
            }
            dao.update(p);
            out.println(obj.writeValueAsString(p));
            bf.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
