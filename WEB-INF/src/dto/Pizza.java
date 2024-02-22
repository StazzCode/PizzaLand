package dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Pizza {
    private int id;
    private String nom;
    private String pate;
    private Set<Ingredient> ingredients;

    public Pizza(@JsonProperty("id") int id, @JsonProperty("nom") String nom, @JsonProperty("pate") String pate, @JsonProperty("ingredients") Set<Ingredient> ingredients){
        this.id = id;
        this.nom = nom;
        this.pate = pate;
        this.ingredients = ingredients;
    }

    public int getId(){
        return this.id;
    }

    public String getNom() {
        return nom;
    }

    public String getPate() {
        return pate;
    }

    public double getPrixBase() {
        double res = 0;
        for (Ingredient i : ingredients){
            res += i.getPrix();
        }
        return res+5;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPate(String pate) {
        this.pate = pate;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
