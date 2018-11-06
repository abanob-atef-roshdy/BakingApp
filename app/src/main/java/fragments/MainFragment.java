package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.MainAdapter;
import bebo.bakingapp.MainActivity;
import bebo.bakingapp.R;
import bebo.bakingapp.RecipeDetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredients;
import models.RecipesModel;
import models.Steps;

public class MainFragment extends Fragment implements MainAdapter.RecipeClickHandler  {
    @BindView(R.id.recipeRecyclerView)
    RecyclerView recipeRec;

    List<RecipesModel> resultList;
    List<Ingredients> ingredientsList;
    List<Steps> stepsList;
    MainAdapter mainAdapter;

    public MainFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        ingredientsList = new ArrayList<>();
      final   Context context = getContext();
        stepsList = new ArrayList<>();
      //  resultList = loadRecipesData(context);
        resultList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipeRec.setLayoutManager(layoutManager);
        recipeRec.setHasFixedSize(true);
        loadRecipesData(context);
        //mainAdapter = new MainAdapter(resultList,context);
//        RecipesModel recipesModel = resultList.get(1);
  //      String name = recipesModel.getName();
    //    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
       // recipeRec.setAdapter(mainAdapter);
        return view;
    }
    public void loadRecipesData(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray all = new JSONArray(response);
                    for(int i =0;i<all.length();i++){
                        JSONObject object1 = all.getJSONObject(i);
                        String name  = object1.getString("name");
                        JSONArray ingredients = object1.getJSONArray("ingredients");
                        for (int e =0;e<ingredients.length();e++){
                            JSONObject object2 = ingredients.getJSONObject(e);
                            int quantity = object2.getInt("quantity");
                            String measure = object2.getString("measure");
                            String ingred = object2.getString("ingredient");
                            Ingredients ingredients1 = new Ingredients(quantity,measure,ingred);
                            ingredientsList.add(ingredients1);
                        }
                        JSONArray steps = object1.getJSONArray("steps");
                        for(int d=0 ; d<steps.length();d++){
                            JSONObject object3 = steps.getJSONObject(d);
                            String shortdesc = object3.getString("shortDescription");
                            String desc = object3.getString("description");
                            String videoUrl = object3.getString("videoURL");
                            String thumbNail = object3.getString("thumbnailURL");
                            Steps steps1 = new Steps(shortdesc,desc,videoUrl,thumbNail);
                            stepsList.add(steps1);
                        }
                        RecipesModel recipesModel = new RecipesModel(name,ingredientsList,stepsList);
                        resultList.add(recipesModel);
                        Context context1 = getContext();
                        mainAdapter = new MainAdapter(resultList,context1, MainFragment.this );
                        recipeRec.setAdapter(mainAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("error", "error");
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }

    });
        queue.add(stringRequest);





    }


    @Override
    public void onRecipeClick(RecipesModel recipesModel) {
        Intent intent = new Intent(getContext(),RecipeDetailActivity.class);
        intent.putExtra("fd",  recipesModel);
        startActivity(intent);

    }
}
