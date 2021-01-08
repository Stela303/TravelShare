package com.example.travelshare.ui.shares_with_me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.ItineraryActivity;
import com.example.travelshare.R;
import com.example.travelshare.adapter.recycler.ItineraryAdapter;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class SharesWithMeFragment extends Fragment {


    View root;
    String userId;
    FirebaseFirestore db;
    RecyclerView recyclerViewItinerary;
    ItineraryAdapter mItineraryAdapter;
    private FirebaseUser signInAccount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_share_with_me, container, false);
        recyclerViewItinerary = root.findViewById(R.id.savedItineraries);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(root.getContext()));
        db=FirebaseFirestore.getInstance();
        signInAccount = FirebaseAuth.getInstance().getCurrentUser();
        if(signInAccount!= null){
            db.collection("users").whereEqualTo("googleID", Objects.requireNonNull(signInAccount).getUid()).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()){
                            DocumentSnapshot user= task1.getResult().getDocuments().get(0);
                            userId = (String) user.getId();
                            this.initializeItinerary();
                        }
                    });
        }else{
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        }
        return root;
    }

    private void initializeItinerary() {
        Query query= db.collection("users").document(userId).collection(Constant.SHARE_WITH_ITINERARIES);
        FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mItineraryAdapter = new ItineraryAdapter(firestoreRecyclerOptions, root.getContext());
        mItineraryAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mItineraryAdapter);
        mItineraryAdapter.startListening();
        mItineraryAdapter.setOnItemClickListener(new ItineraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("ItineraryDocument", documentSnapshot);
                SingletonMap.getInstance().put("Saved", false);
                Intent intent= new Intent(root.getContext(), ItineraryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}