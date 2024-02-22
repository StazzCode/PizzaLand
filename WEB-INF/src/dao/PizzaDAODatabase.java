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

                p = new Pizza(idPizza, nom, pate, null);

                ps = con.prepareStatement(query1);
                ps.setInt(1, id);
                ResultSet rs1 = ps.executeQuery();
                while (rs1.next()) {
                    p.getIngredients().add(daoIngredients.findById(rs1.getInt("id")));
                }
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
        try {
            String request = "UPDATE pizzas SET nom = ? AND pate = ? WHERE id = ?";
            String request1 = "UPDATE associationpizzaingrédients SET idingre = ? WHERE idpizza = ?";
            PreparedStatement ps = con.prepareStatement(request);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPate());
            ps.executeUpdate();

            ps = con.prepareStatement(request1);
            for (Ingredient i : p.getIngredients()){
                ps.setInt(1, i.getId());
                ps.setInt(2, p.getId());
                ps.executeUpdate();
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
