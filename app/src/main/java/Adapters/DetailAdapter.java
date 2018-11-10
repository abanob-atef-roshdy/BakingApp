package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bebo.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Steps;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailAdapterViewHolder> {
    List<Steps> stepsList;
    Context context;
    StepsClickHandler stepsClickHandler;
    public interface StepsClickHandler{
        public void onStepClick(Steps steps);

    }

    public DetailAdapter(List<Steps> stepsList, Context context, StepsClickHandler stepsClickHandler1) {
        this.stepsList = stepsList;
        this.context = context;
        this.stepsClickHandler = stepsClickHandler1;
    }

    @NonNull
    @Override
    public DetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.steps_adapter,viewGroup,false);
        return new DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapterViewHolder detailAdapterViewHolder, int i) {
      Steps steps = stepsList.get(detailAdapterViewHolder.getAdapterPosition());
      detailAdapterViewHolder.steps_tv.setText(steps.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class DetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.steps_tv)
        TextView steps_tv;
        public DetailAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Steps steps = stepsList.get(position);
            stepsClickHandler.onStepClick(steps);
        }
    }
}
