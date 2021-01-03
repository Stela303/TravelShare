package com.example.travelshare.repository;

import android.util.Log;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ItineraryRepository {

    public final String  TAG="Itinerary repository";

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void create(HashMap<String,Object> itinerary){
        db.collection("itineraries")
                .add(itinerary)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void delete(){

    }

    public void update(){

    }


}
