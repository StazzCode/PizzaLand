package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAODatabase;
import dto.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet{
    IngredientDAODatabase dao = new IngredientDAODatabase();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String info = req.getPathInfo();
        if(info == null || info.equals("/")){
            Collection<Ingredient> list = dao.findAll();
            out.println(obj.writeValueAsString(list));
            return;
        }
        String[] splits = info.split("/");
        if(splits.length != 2){
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(splits[1]);
        Ingredient i = dao.findById(id);
        if(i == null){
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        out.println(obj.writeValueAsString(i));
        return;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        ObjectMapper obj = new ObjectMapper();
        PrintWriter out = res.getWriter();
        String data = new BufferedReader(new InputStreamReader(req.getInputStream())).readLine();
        
        Ingredient i = obj.readValue(data, Ingredient.class);
        if(dao.findById(i.getId()) != null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        dao.save(i);
        out.println(data);
    }
}
