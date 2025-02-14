package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Pizza;

public class CommandeDAODatabase implements DAOCommande {
    Connection con;

    public CommandeDAODatabase() {
        this.con = DS.getConnection();
    }

    @Override
    public List<Commande> findAll() {
        ArrayList<Commande> res = new ArrayList<>();
        try {
            String query = "SELECT id FROM commandes";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                res.add(this.findById(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Commande findById(int id) {
        DAOPizza daoPizza = new PizzaDAODatabase();
        Commande c = null;
        try {
            String query = "Select * from commandes Where id = ?";
            String query1 = "Select idpizza from associationcommandepizzas Where idcommande = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idCommande = rs.getInt("id");
                String nom = rs.getString("nom");
                Date date = rs.getDate("date");

                ps = con.prepareStatement(query1);
                ps.setInt(1, id);
                ResultSet rs1 = ps.executeQuery();
                ArrayList<Pizza> pizzas = new ArrayList<>();
                while (rs1.next()) {
                    pizzas.add(daoPizza.findById(rs1.getInt("idpizza")));
                }
                c = new Commande(idCommande, nom, date, pizzas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void save(Commande c) {
        this.save(c.getId(), c.getNom(), c.getDate(), c.getPizzas());
    }

    @Override
    public void save(int id, String nom, Date date, ArrayList<Pizza> pizzas) {
        try {
            String request = "INSERT INTO commandes VALUES(?, ?, ?)";
            String request1 = "INSERT INTO associationcommandepizzas VALUES(?, ?)";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setDate(3, date);
            ps.executeUpdate();

            ps = this.con.prepareStatement(request1);
            for (Pizza p : pizzas) {
                ps.setInt(1, id);
                ps.setInt(2, p.getId());
                ps.executeUpdate();
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double findTotal(int id) {
        return findById(id).getPrixBase();
    }

    @Override
    public void update(Commande c) {
        this.remove(c.getId());
        this.save(c);
    }

    @Override
    public void remove(int id) {
        try {
            String request = "DELETE FROM commandes WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(Commande c) {
        try {
            String request = "SELECT * FROM pizzas WHERE id = ?";
            PreparedStatement ps = this.con.prepareStatement(request);

            for (Pizza p : c.getPizzas()) {
                ps.setInt(1, p.getId());
                ps.executeQuery();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void deletePizza(Commande c, Pizza p) {
        c.getPizzas().remove(p);
        update(c);
    }

    @Override
    public void addPizza(Commande c, Pizza p) {
        c.getPizzas().add(p);
        update(c);
    }

}
