package models;

public class Ingredients {
    String measure,ingredient;
    int quantity;

    public Ingredients(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
