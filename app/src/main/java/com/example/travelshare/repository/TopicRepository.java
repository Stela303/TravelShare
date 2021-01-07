package com.example.travelshare.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class TopicRepository {

    public static final String TOPIC_COLLECTION="topics";
    private final CollectionReference topicRef;

    public TopicRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.topicRef = db.collection(TOPIC_COLLECTION);
    }

    public void searchAllTopics(OnCompleteListener<QuerySnapshot> onCompleteListener){
        this.topicRef.get().addOnCompleteListener(onCompleteListener);
    }


}
