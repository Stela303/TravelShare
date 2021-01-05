package com.example.travelshare.ui.new_itinerary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.ui.adapter.ImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.util.ArrayList;
import java.util.List;

public abstract class AddContentNewItineraryActivity extends AppCompatActivity  {

    protected static final int GALLERY_REQUEST = 2200 ;
    protected Itinerary itinerary;
    protected GalleryPhoto galleryPhoto;
    RecyclerView recyclerViewContent;
    ImageAdapter imageAdapter;
    protected ArrayList<String> urls;
    EditText nameEditText,locationEditText,infoExtraEditText;
    Activity activityReference;

    protected void initializeVariables(int idNameText, int idLocationText, int idInfoExtra,int idRecyclerView, Activity activityReference) {
        this.nameEditText=(EditText) findViewById(idNameText);
        this.locationEditText=(EditText) findViewById(idLocationText);
        this.infoExtraEditText=(EditText)findViewById(idInfoExtra);
        this.recyclerViewContent=(RecyclerView) findViewById(idRecyclerView);
        this.galleryPhoto= new GalleryPhoto(getApplicationContext());
        this.activityReference=activityReference;
        urls=new ArrayList<>();
        imageAdapter = new ImageAdapter(activityReference.getApplicationContext(),this.urls);
        this.recyclerViewContent.setLayoutManager(new LinearLayoutManager(activityReference.getApplicationContext()));
        recyclerViewContent.setAdapter(imageAdapter);
    }

    protected void initializeButtons(int id) {
        FloatingActionButton floatingActionButton= (FloatingActionButton) findViewById(id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                this.galleryPhoto.setPhotoUri(data.getData());
                String photoPath = this.galleryPhoto.getPath();
                System.out.println("URL:" + photoPath);
                this.urls.add(photoPath);
                imageAdapter = new ImageAdapter(activityReference.getApplicationContext(), this.urls);
                this.recyclerViewContent.setLayoutManager(new LinearLayoutManager(activityReference.getApplicationContext()));
                recyclerViewContent.setAdapter(imageAdapter);
            }
        }
    }



    protected void clearText(){
        this.nameEditText.setText("");
        this.locationEditText.setText("");
        this.infoExtraEditText.setText("");
    }

    protected  boolean checkRequiredFields(){
        return this.nameEditText.getText().toString().equals("") &&
        this.locationEditText.getText().toString().equals("")&&
        this.infoExtraEditText.getText().toString().equals("");
    }
}
