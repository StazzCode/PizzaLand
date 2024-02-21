package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private int id;
    private String name;
    private double prix;


    public Ingredient(@JsonProperty("id") int id,@JsonProperty("name") String name, @JsonProperty("prix") double prix) {
        this.id = id;
        this.name = name;
        this.prix = prix;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    
    public double getPrix(){
        return prix;
    }
}