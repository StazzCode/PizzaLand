package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Pizza;

public interface DAOCommande {
    public List<Commande> findAll();

    public Commande findById(int id);

    public void save (Commande c);

    public void save (int id, String nom, Date date, ArrayList<Pizza> pizzas);

    public double findTotal(int id);
}
