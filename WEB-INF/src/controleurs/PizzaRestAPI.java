package controleurs;

import java.io.IOException;
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

@WebServlet("/ingredients/*")
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
            Pizza i = dao.findById(id);
            if(i == null){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if(splits.length == 2){
                out.println(obj.writeValueAsString(i));
            }else if(splits[2].equals("name")){
                out.println(obj.writeValueAsString(i.getNom()));
            }else{
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch(NumberFormatException e){
            out.println(e.getMessage());
        }
        return;
    }
}
