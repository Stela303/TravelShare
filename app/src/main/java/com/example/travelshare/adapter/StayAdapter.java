package com.example.travelshare.adapter;

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


public class StayAdapter extends FirestoreRecyclerAdapter<Stay, StayAdapter.ViewHolder> {

    private OnItemClickListener listener;
    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StayAdapter(@NonNull FirestoreRecyclerOptions<Stay> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Stay model) {
        holder.tittle.setText(model.getName());
        holder.location.setText(model.getLocation());
        holder.info.setText(model.getExtraInfo());
        holder.price.setText(Double.toString(model.getPriceNight()));
        Picasso.with(this.context).load(model.getImages().get(0)).into(holder.image);
        //holder.image.setImageURI(Uri.parse(model.getImage()));
        /*
        if(FirebaseAuth.getInstance().getCurrentUser().getUid()!=null && model.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.user.setText("LO HE SUBIDO YO");
        }*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stay, viewGroup, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tittle;
        TextView location;
        TextView info;
        TextView price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageStay);
            tittle = (TextView) itemView.findViewById(R.id.tittleStay);
            location = (TextView) itemView.findViewById(R.id.locationStay);
            info = (TextView) itemView.findViewById(R.id.infoStay);
            price = (TextView) itemView.findViewById(R.id.priceStay);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

