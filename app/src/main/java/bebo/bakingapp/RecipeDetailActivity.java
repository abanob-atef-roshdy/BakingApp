package bebo.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import fragments.DetailFragment;
import fragments.VideoFragment;
import models.Ingredients;
import models.RecipesModel;
import models.Steps;

public class RecipeDetailActivity extends AppCompatActivity {
    List<Ingredients> ingredList;
    RecipesModel recipesModel;
    DetailFragment detailFragment;
    private Boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intent = getIntent();
        detailFragment = new DetailFragment();
        recipesModel = (RecipesModel) intent.getSerializableExtra("fd");
        detailFragment.setRecipesModel(recipesModel,this);

       if(savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment, detailFragment).commit();
       }
        if (findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;
            VideoFragment stepDetailFragment = new VideoFragment();
            stepDetailFragment.setmStep(recipesModel.getStepsList().get(0), recipesModel.getStepsList());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }
    public void replaceFragment(Steps selectedStep) {
         if (mTwoPane) {
            VideoFragment stepDetailFragment = new VideoFragment();
            stepDetailFragment.setmStep(selectedStep, recipesModel.getStepsList());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            VideoFragment stepDetailFragment = new VideoFragment();
            stepDetailFragment.setmStep(selectedStep, recipesModel.getStepsList());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, stepDetailFragment)
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    private void handleBackPressed() {
        if (mTwoPane || findViewById(R.id.step_detail_instructions) == null)
            finish();
        else {
            DetailFragment recipeDetailFragment = new DetailFragment();
            recipeDetailFragment.setRecipesModel(recipesModel, this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, recipeDetailFragment)
                    .commit();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        handleBackPressed();
        return true;
    }






}
