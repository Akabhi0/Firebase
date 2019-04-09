package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase.databinding.AdapterlayoutBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DistaceModel> models;
    private double distance;

    public Adapter(MainActivity mainActivity, ArrayList<DistaceModel> models) {
        this.context = mainActivity;
        this.models = models;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapterlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * Starting latitude and  longitude of the location
         */
        Location startPoint = new Location("current location");
        startPoint.setLatitude(models.get(position).getLatitudeCurrent());
        startPoint.setLongitude(models.get(position).getLongitudeCurrent());
        Log.e("lat" + models.get(position).getLatitudeCurrent(), "long" + models.get(position).getLongitudeCurrent());

        /**
         *Ending of the latitude and longitude
         */
        Location endPoint = new Location("ending location");
        endPoint.setLatitude(models.get(position).getLatitudeFixedOne());
        endPoint.setLongitude(models.get(position).getLongitudeFixedOne());

        distance = startPoint.distanceTo(endPoint);

        holder.getBinding().distance.setText(String.valueOf(new DecimalFormat("##.##").format(distance)));
        holder.getBinding().name.setText(models.get(position).getName());
        holder.getBinding().image.setImageResource(models.get(position).getDrawable());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AdapterlayoutBinding binding;

        public AdapterlayoutBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

//            if (Double.parseDouble(models.get(getAdapterPosition()).get) < 6000) {
//                binding.distance.setTextColor(Color.GREEN);
//            }

            binding.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Double.parseDouble(binding.distance.getText().toString()) < 6000) {
                        Intent intent = new Intent(context, Comments.class);
                        intent.putExtra("name", models.get(getAdapterPosition()).getName());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


}
