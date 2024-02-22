package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private int id;
    private String nom;
    private double prix;


    public Ingredient(@JsonProperty("id") int id,@JsonProperty("nom") String nom, @JsonProperty("prix") double prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }


    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }

    
    public double getPrix(){
        return prix;
    }
}