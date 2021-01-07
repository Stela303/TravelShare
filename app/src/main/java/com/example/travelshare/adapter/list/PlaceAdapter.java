package com.example.travelshare.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class PlaceAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    private List<InterestingPlace> interestingPlaces;
    LayoutInflater inflater;

    public PlaceAdapter(Context context, List<InterestingPlace> interestingPlaces) {
        this.context = context;
        this.interestingPlaces = interestingPlaces;
    }

    @Override
    public int getCount() {
        return interestingPlaces.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        ImageView image;
        TextView tittle;
        TextView location;
        TextView price;
        TextView topic;
        TextView info;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_place, parent, false);

        // Locate the TextViews in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.imagePlace);
        tittle = (TextView) itemView.findViewById(R.id.tittlePlace);
        location = (TextView) itemView.findViewById(R.id.locationPlace);
        price = (TextView) itemView.findViewById(R.id.pricePlace);
        topic = (TextView) itemView.findViewById(R.id.topicPlace);
        info = (TextView) itemView.findViewById(R.id.infoPlace);

        // Capture position and set to the TextViews
        InterestingPlace place = interestingPlaces.get(position);
        tittle.setText(place.getName());
        location.setText(place.getLocation());
        price.setText(Double.toString(place.getPrice()));
        info.setText(place.getExtraInfo());
        topic.setText(place.getTopic());
        Picasso.with(this.context).load(place.getImages().get(0)).into(image);

        return itemView;
    }
}

