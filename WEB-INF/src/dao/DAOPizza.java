package dao;

import java.util.List;
import java.util.Set;

import dto.Ingredient;
import dto.Pizza;

public interface DAOPizza {
    public List<Pizza> findAll();

    public Pizza findById(int id);

    public void save(Pizza i);

    public void save(String nom, String pate, double prixBase, Set<Ingredient> ingredients);

    public void remove(int id);
}
