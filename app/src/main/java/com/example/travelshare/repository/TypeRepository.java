package com.example.travelshare.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class TypeRepository {

    public static final String TYPE_COLLECTION="type";
    private final CollectionReference typeRef;

    public TypeRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.typeRef = db.collection(TYPE_COLLECTION);
    }

    public void searchAllTypes(OnCompleteListener<QuerySnapshot> onCompleteListener){
        this.typeRef.get().addOnCompleteListener(onCompleteListener);
    }
}
