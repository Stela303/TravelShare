package com.example.travelshare.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travelshare.R;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.User;
import com.example.travelshare.library.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository {

    private final CollectionReference userRef;

    public UserRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.userRef = db.collection(Constant.USER_COLLECTION);
    }

    public void searchAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener){
        this.userRef.get().addOnCompleteListener(onCompleteListener);
    }

    public void share(User user, Itinerary itinerary, Context context) {
        this.userRef.document(user.getId()).collection(Constant.SHARE_WITH_ITINERARIES).add(itinerary).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast requiredFieldsToast = Toast.makeText(context, R.string.share_friends,Toast.LENGTH_SHORT);
                requiredFieldsToast.show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast requiredFieldsToast = Toast.makeText(context, R.string.itinerary_add_error, Toast.LENGTH_SHORT);
                        requiredFieldsToast.show();
                    }
                });
    }

    public void searchUserByEmail(OnCompleteListener<QuerySnapshot> onCompleteListener, String email){
        this.userRef.orderBy("email").startAt(email)
                .endAt(email+"\uf8ff").get().addOnCompleteListener(onCompleteListener);
    }

}
