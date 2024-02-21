package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient{
    Connection con;

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
                res.add(new Ingredient(rs.getInt("id"), rs.getString("name")));
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
                res = new Ingredient(rs.getInt("id"), rs.getString("name"));
            }
            
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
   }

    @Override
    public void save(Ingredient i) {
        this.save(i.getId(), i.getName());
    }

    @Override
    public void save(int id, String name) {
        try{
            String request = "INSERT INTO ingredients VALUES(?, ?)";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.executeUpdate();

            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
