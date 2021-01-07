package com.example.travelshare.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.R;
import com.example.travelshare.data.model.Image;
import com.example.travelshare.library.CloudStorage;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{


    private List<String> mUrls;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private CloudStorage cloudStorage=new CloudStorage();
    private Context  context;

    // data is passed into the constructor
    public ImageAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mUrls = new ArrayList<>();
        this.mUrls.addAll(data);
        this.context=context;
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        if(mUrls!=null &&!mUrls.isEmpty())
        try {
            System.out.println(position);
            System.out.println(mUrls);
            Bitmap bitmap= ImageLoader.init().from(mUrls.get(position)).getBitmap();
            holder.imageView.setImageBitmap(bitmap);
            mUrls.set(position,cloudStorage.uploadFile(mUrls.get(position),context).toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mUrls.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mUrls.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
