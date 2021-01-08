package com.example.travelshare.library;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelshare.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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

    public Uri uploadFile(String urlImage, Context context){
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference imageRef;
        if (user != null) {
            // do your stuff
        } else {
            mAuth.signInAnonymously();
            Uri file = Uri.fromFile(new File(urlImage));
             imageRef = storageRef.child("images/"+file.getLastPathSegment());

             Task<Uri> urlTask = imageRef.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast requiredFieldsToast = Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                        return null;
                    } else {
                        return imageRef.getDownloadUrl();
                    }
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Toast requiredFieldsToast = Toast.makeText(context, "PERFE",Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    } else {
                        Toast requiredFieldsToast = Toast.makeText(context,"ERROR",Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                }
            });

             return urlTask.getResult();

    }
        return null;
    }

    public List<String> uploadFiles(List<String> urls,Context context) {
        List<String> images = new ArrayList<>();
        for (String url : urls) {
            images.add(uploadFile(url,context).toString());
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
