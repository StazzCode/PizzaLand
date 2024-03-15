package dao;

import java.util.List;

import dto.Ingredient;

public interface DAOIngredient {
    public List<Ingredient> findAll();

    public Ingredient findById(int id);

    public void save(Ingredient i);

    public void save(int id, String name, double prix);

    public void remove(int id);

    public boolean isInDatabase(Ingredient i);
} 
