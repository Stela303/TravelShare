package com.example.travelshare.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.List;

public class TopicRepository {

    public static final String TOPIC_COLLECTION="topic";
    public static final String GENERICS ="generics";
    private static final String TAG = "Topic's GET" ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<String> getTopics(){
        DocumentSnapshot document;

        DocumentReference docRef = db.collection(TOPIC_COLLECTION).document(GENERICS);

        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d(TAG, "Cached document data: " + document.getData());
                    }else{
                        Log.d(TAG, "Null data");
                    }
                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });
        return
    }

}
