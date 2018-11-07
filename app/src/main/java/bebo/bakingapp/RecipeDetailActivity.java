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
        Intent intent = getIntent();
        detailFragment = new DetailFragment();
        recipesModel = (RecipesModel) intent.getSerializableExtra("fd");
        detailFragment.setRecipesModel(recipesModel);

       getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment,detailFragment).commit();
    }




}
