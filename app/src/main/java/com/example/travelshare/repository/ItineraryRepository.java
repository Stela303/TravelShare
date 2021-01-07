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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.List;

public class ItineraryRepository {

    public  final String  TAG="Itinerary repository";
    public  final String COLLECTION_NAME="itineraries";
    public  final String SUCCESFUL_MESSAGE="DocumentSnapshot added with ID:";
    public  final String ERROR_MESSAGE="Error adding document";
    public FirebaseFirestore db =  FirebaseFirestore.getInstance();


    private static class ItineraryRepositoryHolder {
        private static final ItineraryRepository ourInstance = new ItineraryRepository();
    }

    public static ItineraryRepository getInstance() {
            return ItineraryRepository.ItineraryRepositoryHolder.ourInstance;
    }

    private ItineraryRepository() {

    }

    public void create(Itinerary itinerary, Context context, CloudStorage cloudStorage, String url){
        //itinerary.setCoverPhoto(cloudStorage.uploadaFile(url));
       // updateURLPhotosPlaces(itinerary, cloudStorage);
      //  updateURLPhotosFood(itinerary, cloudStorage);
       // updateURLPhotosStay(itinerary, cloudStorage,context);
        itinerary.setDate_created(new Date());
        db.collection(COLLECTION_NAME)
                .add(itinerary)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, SUCCESFUL_MESSAGE + documentReference.getId());
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_added,Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, ERROR_MESSAGE, e);
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_add_error, Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                });
    }

    private void updateURLPhotosStay(Itinerary itinerary, CloudStorage cloudStorage,Context context) {
       for( Stay stay : itinerary.getStays()){
           List<String> images = cloudStorage.uploadFiles(stay.getImagesLocal(),context);
           stay.setImagesLocal(images);
       }

    }

    private void updateURLPhotosFood(Itinerary itinerary, CloudStorage cloudStorage) {
        for( FoodPlace food : itinerary.getFoodPlaces()){
         //   List<String> images = cloudStorage.uploadFiles(food.getImagesLocal());
         //   food.setImagesLocal(images);
        }
    }

    private void updateURLPhotosPlaces(Itinerary itinerary, CloudStorage cloudStorage) {
        for( InterestingPlace place : itinerary.getInterestingPlaces()){
         //   List<String> images = cloudStorage.uploadFiles(place.getImagesLocal());
          //  place.setImagesLocal(images);
        }
    }


}
