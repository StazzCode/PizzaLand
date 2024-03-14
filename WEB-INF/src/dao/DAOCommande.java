package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dto.Commande;
import dto.Pizza;

public interface DAOCommande {
    public List<Commande> findAll();

    public Commande findById(int id);

    public void save(Commande c);

    public void save(int id, String nom, Date date, ArrayList<Pizza> pizzas);

    public double findTotal(int id);

    public void update(Commande c);

    public boolean isValid(Commande c);

    public void remove(int id);

    public void deletePizza(Commande c, Pizza p);

    public void addPizza(Commande c, Pizza p);
}
