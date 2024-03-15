package dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((pate == null) ? 0 : pate.hashCode());
        result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
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
        Pizza other = (Pizza) obj;
        if (id != other.id)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (pate == null) {
            if (other.pate != null)
                return false;
        } else if (!pate.equals(other.pate))
            return false;
        if (ingredients == null) {
            if (other.ingredients != null)
                return false;
        } else if (!ingredients.equals(other.ingredients))
            return false;
        return true;
    }

    
}
