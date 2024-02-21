package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAOList implements DAOIngredient{
    private List<Ingredient> ingredients;

    public IngredientDAOList() {
        this.ingredients = new ArrayList<>();
        this.ingredients.add(new Ingredient(0, "Lardons"));
        this.ingredients.add(new Ingredient(1, "Jambon"));
        this.ingredients.add(new Ingredient(2, "Saucisse"));
        this.ingredients.add(new Ingredient(3, "Cote de porc"));
        this.ingredients.add(new Ingredient(4, "Bacon"));
    }

    public List<Ingredient> findAll(){
        return this.ingredients;
    }

    public Ingredient findById(int id){
        for(Ingredient i : this.ingredients){
            if(i.getId() == id) return i;
        }
        return null;
    }

    public void save(int id, String name){
        this.ingredients.add(new Ingredient(id, name));
    }

    public void save(Ingredient i){
        this.ingredients.add(i);
    }
}
