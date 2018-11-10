package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import Adapters.DetailAdapter;
import bebo.bakingapp.R;
import bebo.bakingapp.RecipeDetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredients;
import models.RecipesModel;
import models.Steps;

public class DetailFragment extends Fragment implements DetailAdapter.StepsClickHandler  {
    @BindView(R.id.ingredientsText_tv)
    TextView textView;
    RecipesModel recipesModel;
    List<Ingredients> ingredientsList;
    List<Steps> stepsList;
    @BindView(R.id.ingerLabel)
    TextView labelTv;
    @BindView(R.id.stepsRecyclerView)
    RecyclerView recyclerView;
    DetailAdapter detailAdapter;
    private Context mcontext;



    public DetailFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        ButterKnife.bind(this,view);
        if (savedInstanceState != null) {
            recipesModel = (RecipesModel) savedInstanceState.get("recipe");
            stepsList = (List<Steps>) savedInstanceState.get("stepsList");
            ingredientsList = (List<Ingredients>) savedInstanceState.get("ingredList");
        }

        ingredientsList = recipesModel.getIngredientsList();
      for(Ingredients ingredients:ingredientsList){
            String content = ingredients.getQuantity()+ "" + ingredients.getMeasure()+ "" + ingredients.getIngredient();

            textView.append(content + "\n" + "\n");

        }
        stepsList = recipesModel.getStepsList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        detailAdapter = new DetailAdapter(stepsList,getActivity(),DetailFragment.this);
        recyclerView.setAdapter(detailAdapter);


 //       textView.setText(ingredientsList.get);


        return view;
    }
    public void setRecipesModel(RecipesModel recipesModel,Context context){
       this.recipesModel = recipesModel;
       this.mcontext = context;

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("recipe", recipesModel);
        outState.putSerializable("ingredList", (Serializable) ingredientsList);
        outState.putSerializable("stepsList", (Serializable) stepsList);
    }

    @Override
    public void onStepClick(Steps steps) {
        if (mcontext == null)
            mcontext = getContext();
        RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) mcontext;
        recipeDetailActivity.replaceFragment(steps);

    }
}
