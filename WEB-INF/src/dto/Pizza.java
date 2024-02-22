package dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Pizza {
    private int id;
    private String nom;
    private String pate;
    private double prixBase;
    private Set<Ingredient> ingredients;

    public Pizza(@JsonProperty("id") int id, @JsonProperty("nom") String nom, @JsonProperty("pate") String pate, @JsonProperty("ingredients") Set<Ingredient> ingredients){
        this.id = id;
        this.nom = nom;
        this.pate = pate;
        this.ingredients = ingredients;
        for (Ingredient i : ingredients){
            this.prixBase += i.getPrix();
        }
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
        return prixBase;
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
