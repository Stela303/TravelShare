package com.example.travelshare.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.travelshare.R;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.SingletonMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class ItineraryRepository {

    public  final String  TAG="Itinerary repository";
    public  final String COLLECTION_NAME="itineraries";
    public  final String SUCCESFUL_MESSAGE="";
    public  final String ERROR_MESSAGE="";
    public FirebaseFirestore db =  FirebaseFirestore.getInstance();


    private static class ItineraryRepositoryHolder {
        private static final ItineraryRepository ourInstance = new ItineraryRepository();
    }

    public static ItineraryRepository getInstance() {
            return ItineraryRepository.ItineraryRepositoryHolder.ourInstance;
    }

    private ItineraryRepository() {

    }

    public void create(Itinerary itinerary, Context context){
        itinerary.setDate_created(new Date());
        db.collection(COLLECTION_NAME)
                .add(itinerary)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_added,Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_add_error, Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                });
    }

    public void delete(){

    }

    public void update(){

    }


}
