package dao;

import java.util.List;
import java.util.Set;

import dto.Ingredient;
import dto.Pizza;

public interface DAOPizza {
    public List<Pizza> findAll();

    public Pizza findById(int id);

    public void save(Pizza i);

    public void save(int id, String nom, String pate, Set<Ingredient> ingredients);

    public void remove(int id);

    public void update(Pizza p);

    public void addIngredients(Pizza p, Ingredient i);
}
