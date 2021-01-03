package com.example.travelshare.ui.new_itinerary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.travelshare.R;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewPlaceActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 2200 ;
    private Itinerary itinerary;
    private GalleryPhoto galleryPhoto;

    EditText nameEditText;
    EditText locationEditText;
    Spinner  topicSpinner;
    EditText priceEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SingletonMap.getInstance().containsKey(Constant.ITINERARY_KEY)){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_new_place);
            this.itinerary=(Itinerary) SingletonMap.getInstance().get(Constant.ITINERARY_KEY);
            initializeVariables();
            initializeButtons();
        }
    }

    private void initializeVariables() {
        this.nameEditText=(EditText) findViewById(R.id.editTextName);
        this.locationEditText=(EditText) findViewById(R.id.editTextLocation);
        this.priceEditText=(EditText)findViewById(R.id.editTextPrice);
        this.galleryPhoto= new GalleryPhoto(getApplicationContext());
    }

    private void initializeButtons() {
        FloatingActionButton floatingActionButton= (FloatingActionButton) findViewById(R.id.btn_add_image);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
    }

    private void addPlaceOfInterest(){
        InterestingPlace interestingPlace=new InterestingPlace();
        interestingPlace.setName(this.nameEditText.getText().toString());
        interestingPlace.setLocation(this.locationEditText.getText().toString());
        interestingPlace.setPrice((Double.parseDouble(this.priceEditText.getText().toString())));
        this.itinerary.addInterestingPlace(interestingPlace);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY,this.itinerary);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                System.out.println(photoPath);
            }
        }
    }


    }