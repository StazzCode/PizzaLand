package dto;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commande {
    private int id;
    private String nom;
    private Date date;
    private ArrayList<Pizza> pizzas;

    public Commande(@JsonProperty("id") int id, @JsonProperty("nom") String nom, @JsonProperty("date") Date date, @JsonProperty("pizzas") ArrayList<Pizza> pizzas){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.pizzas = pizzas;
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
        double prixBase = 0;
        for (Pizza p : pizzas){
            prixBase += p.getPrixBase();
        }
        return prixBase;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((pizzas == null) ? 0 : pizzas.hashCode());
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
        Commande other = (Commande) obj;
        if (id != other.id)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (pizzas == null) {
            if (other.pizzas != null)
                return false;
        } else if (!pizzas.equals(other.pizzas))
            return false;
        return true;
    }

    
}
