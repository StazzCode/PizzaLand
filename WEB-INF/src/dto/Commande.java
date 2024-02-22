package dto;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commande {
    private int id;
    private String nom;
    private Date date;
    private ArrayList<Pizza> pizzas;
    private double prixBase;

    public Commande(@JsonProperty("id") int id, @JsonProperty("nom") String nom, @JsonProperty("date") Date date, @JsonProperty("pizzas") ArrayList<Pizza> pizzas, @JsonProperty("prixBase") double prixBase){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.pizzas = pizzas;
        for (Pizza p : pizzas){
            this.prixBase += p.getPrixBase();
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public double getPrixBase() {
        return prixBase;
    }
}
