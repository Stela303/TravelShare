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
import com.example.travelshare.data.model.Stay;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StayAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    List<Stay> stays;
    LayoutInflater inflater;

    public StayAdapter(Context context, List<Stay> stays) {
        this.context = context;
        this.stays = stays;
    }

    @Override
    public int getCount() {
        return stays.size();
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
        TextView info;
        TextView price;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_stay, parent, false);

        // Locate the TextViews in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.imageStay);
        tittle = (TextView) itemView.findViewById(R.id.tittleStay);
        location = (TextView) itemView.findViewById(R.id.locationStay);
        info = (TextView) itemView.findViewById(R.id.infoStay);
        price = (TextView) itemView.findViewById(R.id.priceStay);

        // Capture position and set to the TextViews
        Stay stay = stays.get(position);
        tittle.setText(stay.getName());
        location.setText(stay.getLocation());
        info.setText(stay.getExtraInfo());
        price.setText(Double.toString(stay.getPriceNight()));
        Picasso.with(this.context).load(stay.getImages().get(0)).into(image);

        return itemView;
    }
}

