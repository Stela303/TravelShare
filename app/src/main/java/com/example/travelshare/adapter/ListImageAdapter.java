package com.example.travelshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.travelshare.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListImageAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    List<String> images;
    LayoutInflater inflater;

    public ListImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
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
        ImageView imgImg;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_image, parent, false);

        // Locate the TextViews in listview_item.xml
        imgImg = (ImageView) itemView.findViewById(R.id.imageIt);

        // Capture position and set to the TextViews
        Picasso.with(this.context).load(images.get(position)).into(imgImg);

        return itemView;
    }
}
