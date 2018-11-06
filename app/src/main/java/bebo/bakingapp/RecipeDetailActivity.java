package bebo.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import fragments.DetailFragment;
import models.Ingredients;
import models.RecipesModel;

public class RecipeDetailActivity extends AppCompatActivity {
    List<Ingredients> ingredList;
    RecipesModel recipesModel;
    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        recieveData();
    }
    public void recieveData(){
        Intent intent = getIntent();
        recipesModel = (RecipesModel) intent.getSerializableExtra("fd");
        detailFragment.setRecipesModel(recipesModel);


    }
}
