package com.example.travelshare.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TopicRepository {

    public static final String TOPIC_COLLECTION="topic";
    public static final String GENERICS ="generics";
    private static final String TAG = "Topic's GET" ;
    private final FirebaseFirestore db;
    private final CollectionReference topicRef;

    public TopicRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.topicRef = db.collection(TOPIC_COLLECTION);
    }

    public void searchAllTopics(OnCompleteListener<QuerySnapshot> onCompleteListener){
        this.topicRef.get().addOnCompleteListener(onCompleteListener);
    }


}
