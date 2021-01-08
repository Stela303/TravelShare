package com.example.travelshare.adapter.recycler.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.R;
import com.example.travelshare.data.model.FoodPlace;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    List<FoodPlace> food;
    private OnFoodListener mOnFoodListener;
    public FoodAdapter(Context context, List<FoodPlace> food, OnFoodListener onFoodListener) {
        this.context=context;
        this.food=food;
        this.mOnFoodListener=onFoodListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        return new ViewHolder(view, this.mOnFoodListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tittle.setText(food.get(position).getName());
        holder.location.setText(food.get(position).getLocation());
        holder.type.setText(food.get(position).getType());
        holder.category.setText(food.get(position).getCategory());
        holder.info.setText(food.get(position).getExtraInfo());
        holder.price.setText(Double.toString(food.get(position).getAveragePrice()));
        Picasso.with(this.context).load(food.get(position).getImages().get(0)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return food.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView tittle;
        TextView location;
        TextView type;
        TextView category;
        TextView info;
        TextView price;
        OnFoodListener mOnFoodListener;

        public ViewHolder(@NonNull View itemView, OnFoodListener onFoodListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageFood);
            tittle = (TextView) itemView.findViewById(R.id.tittleFood);
            location = (TextView) itemView.findViewById(R.id.locationFood);
            type = (TextView) itemView.findViewById(R.id.typeFood);
            category = (TextView) itemView.findViewById(R.id.categoryFood);
            info = (TextView) itemView.findViewById(R.id.infoFood);
            price = (TextView) itemView.findViewById(R.id.priceFood);
            mOnFoodListener = onFoodListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mOnFoodListener.onFoodClick(getAdapterPosition());
        }

    }

    public interface OnFoodListener{
        void onFoodClick(int position);
    }
}