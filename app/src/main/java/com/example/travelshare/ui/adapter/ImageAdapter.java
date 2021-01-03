package com.example.travelshare.ui.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.R;
import com.example.travelshare.ui.model.ImageShow;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends FirestoreRecyclerAdapter<ImageShow, ImageAdapter.ViewHolder> {

    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ImageAdapter(@NonNull FirestoreRecyclerOptions<ImageShow> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ImageShow model) {
        Picasso.with(this.context).load(model.getUrl()).into(holder.image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
