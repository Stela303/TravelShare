package com.example.travelshare.library;

import android.net.Uri;

import com.example.travelshare.data.model.Itinerary;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CloudStorage {

    FirebaseStorage storage;
    StorageReference storageRef;

    public CloudStorage() {
        this.storage = FirebaseStorage.getInstance(Constant.CLOUDSTORAGE_DIR);
        this.storageRef=this.storage.getReference();
    }

    public void uploadFile(String urlImage, List<String> urls) {
        StorageReference imageRef;
        Uri file = Uri.fromFile(new File(urlImage));
        imageRef = storageRef.child("images/" + file.getLastPathSegment());
        urls.add("images/" +file.getLastPathSegment());
        imageRef.putFile(file);
}

    public void uploadCoverPhoto(String urlImage, Itinerary itinerary) {
        StorageReference imageRef;
        Uri file = Uri.fromFile(new File(urlImage));
        itinerary.setImage("images/" +file.getLastPathSegment());
        imageRef = storageRef.child("images/" + file.getLastPathSegment());
        imageRef.putFile(file);
    }

    public void uploadFiles(List<String> urls, List<String> urlsFirebase) {
        for (String url : urls) {
            uploadFile(url,urlsFirebase);
        }
    }

}