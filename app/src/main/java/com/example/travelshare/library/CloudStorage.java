package com.example.travelshare.library;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CloudStorage {

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public CloudStorage() {
        this.storage = FirebaseStorage.getInstance("gs://travelshare-c0541.appspot.com/");
        this.storageRef=this.storage.getReference();
    }

    public String uploadFile(String urlImage, Context context) {
        final String[] uri = new String[1];
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference imageRef;

            Uri file = Uri.fromFile(new File(urlImage));
            imageRef = storageRef.child("images/" + file.getLastPathSegment());

            imageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                Task<Uri> url= taskSnapshot.getStorage().getDownloadUrl();
                                                                uri[0] =url.toString();
                                                                System.out.println(uri[0]);
                                                            }
                                                        }

            );


        return uri[0];
    }

    public List<String> uploadFiles(List<String> urls, Context context) {
        List<String> images = new ArrayList<>();
        for (String url : urls) {
            images.add(uploadFile(url, context));
        }
        return images;
    }


    private void signInAnonymously(String urlImage) {
        StorageReference ref=null;
        mAuth.signInAnonymously().addOnSuccessListener((Executor) this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Uri file = Uri.fromFile(new File(urlImage));
                StorageReference imageRef = storageRef.child("images/"+file.getLastPathSegment());
                UploadTask uploadTask = imageRef.putFile(file);
            }
        }).addOnFailureListener((Executor) this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("", "signInAnonymously:FAILURE", exception);
            }

        });
    }
}