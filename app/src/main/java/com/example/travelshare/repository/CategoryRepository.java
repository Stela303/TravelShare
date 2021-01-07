package com.example.travelshare.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CategoryRepository {

    public static final String CATEGORY_COLLECTION="category";
    private final CollectionReference categoryRef;

    public CategoryRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.categoryRef = db.collection(CATEGORY_COLLECTION);
    }

    public void searchAllCategories(OnCompleteListener<QuerySnapshot> onCompleteListener){
        this.categoryRef.get().addOnCompleteListener(onCompleteListener);
    }

}

