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
import com.example.travelshare.data.model.Stay;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;


public class StayAdapter extends RecyclerView.Adapter<StayAdapter.ViewHolder> {

    Context context;
    List<Stay> stay;
    private OnStayListener mOnStayListener;
    public StayAdapter(Context context, List<Stay> stay, OnStayListener onStayListener) {
        this.context=context;
        this.stay=stay;
        this.mOnStayListener=onStayListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tittle.setText(stay.get(position).getName());
        holder.location.setText(stay.get(position).getLocation());
        holder.info.setText(stay.get(position).getExtraInfo());
        holder.price.setText(Double.toString(stay.get(position).getPriceNight()));
        Picasso.with(this.context).load(stay.get(position).getImages().get(0)).into(holder.image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stay, viewGroup, false);
        return new ViewHolder(view, this.mOnStayListener);
    }

    @Override
    public int getItemCount() {
        return stay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView tittle;
        TextView location;
        TextView info;
        TextView price;
        OnStayListener mOnStayListener;

        public ViewHolder(@NonNull View itemView, OnStayListener onStayListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageStay);
            tittle = (TextView) itemView.findViewById(R.id.tittleStay);
            location = (TextView) itemView.findViewById(R.id.locationStay);
            info = (TextView) itemView.findViewById(R.id.infoStay);
            price = (TextView) itemView.findViewById(R.id.priceStay);
            mOnStayListener = onStayListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mOnStayListener.onStayClick(getAdapterPosition());
        }

    }
    public interface OnStayListener{
        void onStayClick(int position);
    }
}

