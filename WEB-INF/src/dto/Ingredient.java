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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (id != other.id)
            return false;
        return true;
    }

    
}