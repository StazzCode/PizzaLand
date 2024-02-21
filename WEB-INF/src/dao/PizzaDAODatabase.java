package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAODatabase implements DAOPizza {
    Connection con;

    public PizzaDAODatabase(){
        this.con = DS.getConnection();
    }

    @Override
    public List<Pizza> findAll() {
        ArrayList<Pizza> res = new ArrayList<>();
        try {
            String query = "SELECT * FROM pizzas;";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs  = ps.executeQuery();

            while(rs.next()){
                String nom = rs.getString("nom");
                String pate = rs.getString("pate");
                Set<Ingredient> ingredients = (Set<Ingredient>) rs.getObject("ingredients");

                res.add(new Pizza(nom, pate, ingredients));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Pizza findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void save(Pizza i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void save(String nom, String pate, double prixBase, Set<Ingredient> ingredients) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
