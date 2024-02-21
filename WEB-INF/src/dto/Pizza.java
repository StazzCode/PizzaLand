package dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pizza {
    private String nom;
    private String pate;
    private double prixBase;
    private Set<Ingredient> ingredients;

    public Pizza(@JsonProperty("nom") String nom, @JsonProperty("pate") String pate, @JsonProperty("ingredients") Set<Ingredient> ingredients){
        this.nom = nom;
        this.pate = pate;
        this.ingredients = ingredients;
        for (Ingredient i : ingredients){
            this.prixBase += i.getPrix();
        }
    }

    public String getNom() {
        return nom;
    }

    public String getPate() {
        return pate;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
}
