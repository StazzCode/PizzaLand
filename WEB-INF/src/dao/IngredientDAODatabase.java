package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient{
    private Connection con;
    private static int cpt = 0;

    public IngredientDAODatabase() {
        this.con = DS.getConnection();
    }

    @Override
    public List<Ingredient> findAll(){
        ArrayList<Ingredient> res = new ArrayList<>();
        try{
            String request = "SELECT * FROM ingredients";
            Statement stmt = this.con.createStatement();
            ResultSet rs =  stmt.executeQuery(request);

            while(rs.next()){
                res.add(new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("prix")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Ingredient findById(int id) {
        Ingredient res = null;
        try{
            String request = "SELECT * FROM ingredients WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
  
            if (rs.next()) {
                res = new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("prix"));
            }
            
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
   }

    @Override
    public void save(Ingredient i) {
        this.save(i.getId(), i.getName(), i.getPrix());
    }

    @Override
    public void save(int id, String name, double prix) {
        try{
            String request = "INSERT INTO ingredients VALUES(?, ?, ?)";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, prix);
            ps.executeUpdate();

            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try{
            String request = "DELETE FROM ingredients WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {
        try{
            String request = "DROP TABLE IF EXISTS ingredients";
            Statement stmt = this.con.createStatement();
            stmt.executeUpdate(request);
            
            request = "CREATE TABLE ingredients(id int, name text, prix float)";
            stmt.executeUpdate(request);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}