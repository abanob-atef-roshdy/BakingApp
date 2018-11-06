package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bebo.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredients;
import models.RecipesModel;
import models.Steps;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>  {
   private List<RecipesModel> resultList;
    private Context context;
    private final RecipeClickHandler recipeClickHandler;



    public interface RecipeClickHandler{
        public void onRecipeClick(RecipesModel recipesModel);
    }

    public MainAdapter(List<RecipesModel> resultList, Context context,RecipeClickHandler recipeClickHandle) {
        this.recipeClickHandler = recipeClickHandle;
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.name_adapter, viewGroup, false);
        return new MainAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterViewHolder mainAdapterViewHolder, int i) {
     RecipesModel recipesModel = resultList.get(i);

     mainAdapterViewHolder.name_tv.setText(recipesModel.getName());
       }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

  public class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.name_tv)
       TextView name_tv;

        public MainAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

      @Override
      public void onClick(View view) {
          int position = getAdapterPosition();
          RecipesModel recipesModel = resultList.get(position);
          recipeClickHandler.onRecipeClick(recipesModel);
      }
  }
}
