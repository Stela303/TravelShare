package com.example.travelshare.adapter.recycler.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.R;
import com.example.travelshare.data.model.InterestingPlace;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    Context context;
    private OnPlaceListener mOnPlaceListener;
    private List<InterestingPlace> places;
    public PlaceAdapter(Context context, List<InterestingPlace> places, OnPlaceListener onPlaceListener) {
        this.context=context;
        this.places=places;
        this.mOnPlaceListener=onPlaceListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);
        return new ViewHolder(view, this.mOnPlaceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tittle.setText(places.get(position).getName());
        holder.location.setText(places.get(position).getLocation());
        holder.price.setText(Double.toString(places.get(position).getPrice()));
        holder.info.setText(places.get(position).getExtraInfo());
        holder.topic.setText(places.get(position).getTopic());
        Picasso.with(this.context).load(places.get(position).getImages().get(0)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView tittle;
        TextView location;
        TextView price;
        TextView topic;
        TextView info;
        OnPlaceListener mOnPlaceListener;

        public ViewHolder(@NonNull View itemView, OnPlaceListener onPlaceListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imagePlace);
            tittle = (TextView) itemView.findViewById(R.id.tittlePlace);
            location = (TextView) itemView.findViewById(R.id.locationPlace);
            price = (TextView) itemView.findViewById(R.id.pricePlace);
            topic = (TextView) itemView.findViewById(R.id.topicPlace);
            info = (TextView) itemView.findViewById(R.id.infoPlace);
            mOnPlaceListener = onPlaceListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mOnPlaceListener.onPlaceClick(getAdapterPosition());
        }

    }
    public interface OnPlaceListener{
        void onPlaceClick(int position);
    }
}

