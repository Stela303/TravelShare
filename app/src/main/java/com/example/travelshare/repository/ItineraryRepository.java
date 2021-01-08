package com.example.travelshare.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.travelshare.R;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.Stay;
import com.example.travelshare.library.CloudStorage;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItineraryRepository {

    public  final String  TAG="Itinerary repository";
    public  final String COLLECTION_NAME="itineraries";
    public  final String SUCCESFUL_MESSAGE="DocumentSnapshot added with ID:";
    public  final String ERROR_MESSAGE="Error adding document";
    private final FirebaseFirestore db;
    private final CloudStorage cloudStorage;



    private static class ItineraryRepositoryHolder {
        private static final ItineraryRepository ourInstance = new ItineraryRepository();
    }

    public static ItineraryRepository getInstance() {
            return ItineraryRepository.ItineraryRepositoryHolder.ourInstance;
    }

    private ItineraryRepository() {
        this.db =  FirebaseFirestore.getInstance();
        this.cloudStorage=new CloudStorage();
    }

    public void create(Itinerary itinerary, Context context){
        cloudStorage.uploadCoverPhoto(itinerary.getImage(),itinerary);
        updateURLPhotosPlaces(itinerary);
        updateURLPhotosFood(itinerary);
        updateURLPhotosStay(itinerary);
        itinerary.setDate_created(new Date());
        db.collection(COLLECTION_NAME)
                .add(itinerary)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, SUCCESFUL_MESSAGE + documentReference.getId());
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_added,Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                        SingletonMap.getInstance().remove(Constant.ITINERARY_KEY);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, ERROR_MESSAGE, e);
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_add_error, Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                        SingletonMap.getInstance().remove(Constant.ITINERARY_KEY);
                    }
                });
    }

      private void updateURLPhotosStay(Itinerary itinerary) {
        List<String> images=new ArrayList<>();
       for( Stay stay : itinerary.getStays()){
            cloudStorage.uploadFiles(stay.getImages(),images);
           stay.setImages(images);
       }

    }

    private void updateURLPhotosFood(Itinerary itinerary) {
        List<String> images=new ArrayList<>();
        for( FoodPlace food : itinerary.getFoodPlaces()){
           cloudStorage.uploadFiles(food.getImages(),images);
           food.setImages(images);
        }
    }

    private void updateURLPhotosPlaces(Itinerary itinerary) {
        List<String> images=new ArrayList<>();
        for( InterestingPlace place : itinerary.getInterestingPlaces()){
           cloudStorage.uploadFiles(place.getImages(),images);
           place.setImages(images);
        }
    }

}
