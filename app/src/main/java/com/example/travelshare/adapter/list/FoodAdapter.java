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
import com.example.travelshare.data.model.FoodPlace;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    List<FoodPlace> foodPlaces;
    LayoutInflater inflater;

    public FoodAdapter(Context context, List<FoodPlace> foodPlaces) {
        this.context = context;
        this.foodPlaces = foodPlaces;
    }

    @Override
    public int getCount() {
        return foodPlaces.size();
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
        TextView type;
        TextView category;
        TextView info;
        TextView price;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_food, parent, false);

        // Locate the TextViews in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.imageFood);
        tittle = (TextView) itemView.findViewById(R.id.tittleFood);
        location = (TextView) itemView.findViewById(R.id.locationFood);
        type = (TextView) itemView.findViewById(R.id.typeFood);
        category = (TextView) itemView.findViewById(R.id.categoryFood);
        info = (TextView) itemView.findViewById(R.id.infoFood);
        price = (TextView) itemView.findViewById(R.id.priceFood);

        // Capture position and set to the TextViews
        FoodPlace food= foodPlaces.get(position);
        tittle.setText(food.getName());
        location.setText(food.getLocation());
        type.setText(food.getType());
        category.setText(food.getCategory());
        info.setText(food.getExtraInfo());
        price.setText(Double.toString(food.getAveragePrice()));
        Picasso.with(this.context).load(food.getImages().get(0)).into(image);

        return itemView;
    }
}

