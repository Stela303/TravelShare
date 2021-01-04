package com.example.travelshare;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.ui.Itinerary;
import com.example.travelshare.ui.home.HomeFragment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Adapter extends FirestoreRecyclerAdapter<Itinerary, Adapter.ViewHolder> {

    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(@NonNull FirestoreRecyclerOptions<Itinerary> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Itinerary model) {
        holder.tittle.setText(model.getTittle());
        holder.location.setText(model.getLocation());
        holder.rating.setText(String.format("Puntuación: "));
        holder.thematic.setText(model.getThematic());
        Picasso.with(this.context).load(model.getImage()).into(holder.image);
        //holder.image.setImageURI(Uri.parse(model.getImage()));
        /*
        if(FirebaseAuth.getInstance().getCurrentUser().getUid()!=null && model.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.user.setText("LO HE SUBIDO YO");
        }*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_itinerary, viewGroup, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView tittle;
        TextView location;
        TextView user;
        TextView rating;
        TextView thematic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            tittle = (TextView) itemView.findViewById(R.id.tittle);
            location = (TextView) itemView.findViewById(R.id.location);
            user = (TextView) itemView.findViewById(R.id.user);
            rating = (TextView) itemView.findViewById(R.id.rating);
            thematic = (TextView) itemView.findViewById(R.id.thematic);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), getItem(getLayoutPosition()).getTittle(), Toast.LENGTH_SHORT).show();
        }

    }
}

