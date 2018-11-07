package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bebo.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredients;
import models.RecipesModel;

public class DetailFragment extends Fragment  {
    @BindView(R.id.ingredientsText_tv)
    TextView textView;
    RecipesModel recipesModel;
    List<Ingredients> ingredientsList;
    @BindView(R.id.ingerLabel)
    TextView labelTv;
    public DetailFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        ButterKnife.bind(this,view);

        ingredientsList = recipesModel.getIngredientsList();
      for(Ingredients ingredients:ingredientsList){
            String content = ingredients.getQuantity()+ "" + ingredients.getMeasure()+ "" + ingredients.getIngredient();

            textView.append(content + "\n");

        }


 //       textView.setText(ingredientsList.get);


        return view;
    }
    public void setRecipesModel(RecipesModel recipesModel){
        this.recipesModel = recipesModel;

    }

}
