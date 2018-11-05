package models;

import java.util.List;

public class RecipesModel {
    String name;
    List<Ingredients> ingredientsList;
    List<Steps> stepsList;

    public RecipesModel(String name, List<Ingredients> ingredientsList, List<Steps> stepsList) {
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }
}
