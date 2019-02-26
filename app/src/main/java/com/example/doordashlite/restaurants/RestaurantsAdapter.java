package com.example.doordashlite.restaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doordashlite.R;
import com.example.doordashlite.data.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private RestaurantsViewModel viewModel;
    private List<Restaurant> data;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup container;

        // each data item is just a string in this case
        @BindView(R.id.item_cover_img)
        ImageView coverImage;

        @BindView(R.id.item_name)
        TextView nameView;

        @BindView(R.id.item_description)
        TextView descriptionView;

        @BindView(R.id.item_distance)
        TextView distanceView;

        ViewHolder(ViewGroup item) {
            super(item);
            container = item;
            ButterKnife.bind(this, container);
        }
    }

    RestaurantsAdapter(RestaurantsViewModel viewModel) {
        this.viewModel = viewModel;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_restraurant_item, parent, false);
        return new RestaurantsAdapter.ViewHolder((ViewGroup) item);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsAdapter.ViewHolder holder, int position) {
        final Restaurant restaurant = data.get(position);

//        holder.coverImage.setImageURI(restaurant.coverImgUrl);
        Picasso.get()
                .load(restaurant.coverImgUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.coverImage);

        holder.nameView.setText(restaurant.name);
        holder.descriptionView.setText(restaurant.description);
        holder.distanceView.setText(restaurant.id);

        holder.container.setClickable(true);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: open restaurant detail activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Restaurant> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
