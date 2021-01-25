package com.oxs.ocdxsunnah.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxs.ocdxsunnah.Models.OlahragaModels;
import com.oxs.ocdxsunnah.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OlahragaAdapter extends RecyclerView.Adapter<OlahragaAdapter.viewHolder> {

    private List<OlahragaModels.Data> results = new ArrayList<>();


    public OlahragaAdapter(List<OlahragaModels.Data> results){
        this.results = results;
    }

    @NonNull
    @Override
    public OlahragaAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_olahraga,parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        OlahragaModels.Data result = results.get(position);

        Picasso.get().load(result.getImage()).into(holder.ivOlahraga);
        holder.tvOlahraga.setText(result.getTitle());
        holder.tvKalori.setText("Kalori Terbakar : "+result.getKalori());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView ivOlahraga;
        TextView tvOlahraga, tvKalori;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivOlahraga = itemView.findViewById(R.id.ivOlahrga);
            tvOlahraga = itemView.findViewById(R.id.tvOlahraga);
            tvKalori = itemView.findViewById(R.id.tvKalori);
        }
    }


    public void setData(List<OlahragaModels.Data> data){
        results.clear();
        results.addAll(data);
        notifyDataSetChanged();
    }
}
