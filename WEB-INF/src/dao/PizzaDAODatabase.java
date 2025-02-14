package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAODatabase implements DAOPizza {
    Connection con;

    public PizzaDAODatabase() {
        this.con = DS.getConnection();
    }

    @Override
    public List<Pizza> findAll() {
        ArrayList<Pizza> res = new ArrayList<>();
        try {
            String query = "SELECT id FROM pizzas";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs  = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                res.add(this.findById(id));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Pizza findById(int id) {
        DAOIngredient daoIngredients = new IngredientDAODatabase();
        Pizza p = null;
        try {
            String query = "Select * from pizzas Where id = ?";
            String query1 = "Select idingre from associationpizzaingrédients Where idpizza = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idPizza = rs.getInt("id");
                String nom = rs.getString("nom");
                String pate = rs.getString("pate");
                
                ps = con.prepareStatement(query1);
                ps.setInt(1, id);
                ResultSet rs1 = ps.executeQuery();
                HashSet<Ingredient> ingredients = new HashSet<>();
                while (rs1.next()) {
                    ingredients.add(daoIngredients.findById(rs1.getInt("idingre")));
                }
                p = new Pizza(idPizza, nom, pate, ingredients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public void save(Pizza i) {
        this.save(i.getId(), i.getNom(), i.getPate(), i.getIngredients());
    }

    @Override
    public void save(int id, String nom, String pate, Set<Ingredient> ingredients) {
        try{
            String request = "INSERT INTO pizzas VALUES(?, ?, ?)";
            String request1 = "INSERT INTO associationpizzaingrédients VALUES(?, ?)";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setString(3, pate);
            ps.executeUpdate();

            ps = this.con.prepareStatement(request1);
            for (Ingredient i : ingredients){
                ps.setInt(1, id);
                ps.setInt(2, i.getId());
                ps.executeUpdate();
            }

            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try{
            String request = "DELETE FROM pizzas WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pizza p) {
        this.remove(p.getId());
        this.save(p);
    }

    @Override
    public void addIngredients(Pizza p, Ingredient i) {
        p.getIngredients().add(i);
        update(p);
    }

    @Override
    public void deleteIngredient(Pizza p, Ingredient i){
        p.getIngredients().remove(i);
        update(p);
    }

    @Override
    public boolean isValid(Pizza p){
        try {
            String request = "SELECT * FROM ingredients WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);

            for(Ingredient i : p.getIngredients()){
                ps.setInt(1, i.getId());
                ps.executeQuery();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isInDatabase(Pizza i){
        boolean res = false;
        try {
            String request = "SELECT * FROM pizzas WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, i.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}