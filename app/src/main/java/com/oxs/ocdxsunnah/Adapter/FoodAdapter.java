package com.oxs.ocdxsunnah.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxs.ocdxsunnah.Models.FoodModels;
import com.oxs.ocdxsunnah.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.viewHolder> {
    private List<FoodModels.Data> results = new ArrayList<>();


    public FoodAdapter(List<FoodModels.Data> results){
        this.results = results;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_food, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodModels.Data result = results.get(position);
        Picasso.get().load(result.getImage()).into(holder.ivMakan);
        holder.tvTitle.setText(result.getTitle());
        holder.tvQty.setText("Qty : "+result.getQty());
        holder.tvEnergy.setText("Energy : " + result.getEnergy()+" Kcal");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView ivMakan;
        TextView tvTitle, tvQty, tvEnergy;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivMakan = itemView.findViewById(R.id.ivMakan);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvEnergy = itemView.findViewById(R.id.tvEnergi);

        }
    }

    public void setData(List<FoodModels.Data> data){

        results.clear();
        results.addAll(data);
        notifyDataSetChanged();
    }
}
