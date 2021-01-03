package com.example.travelshare.ui.saved;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelshare.Adapter;
import com.example.travelshare.R;
import com.example.travelshare.ui.Itinerary;
import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SavedFragment extends Fragment {

    View root;
    FirebaseFirestore db;
    RecyclerView recyclerViewItinerary;
    Adapter mAdapter;
    TabItem mine;
    TabItem other;
    private FirebaseUser signInAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerViewItinerary = root.findViewById(R.id.savedItineraries);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(root.getContext()));

        signInAccount = FirebaseAuth.getInstance().getCurrentUser();
        //signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount== null){
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        }

/*
        mine = (TabItem) root.findViewById(R.id.myItineraries);
        mine.setOnClickListener(v -> {
            Query query= db.collection("users/"+signInAccount.getUid()+"/itineraries").whereEqualTo("prublisher",false);
            this.initializeItinerary(query);
            Toast.makeText(root.getContext(), "MIS ITINERARIOS GUARDADOS", Toast.LENGTH_SHORT).show();
        });
        other = (TabItem) root.findViewById(R.id.otherItineraries);
        other.setOnClickListener(v -> {
            Query query= db.collection("users/"+signInAccount.getUid()+"/saved");
            this.initializeItinerary(query);
            Toast.makeText(root.getContext(), "ITINERARIOS DE OTROS GUARDADOS", Toast.LENGTH_SHORT).show();
        });*/

        return root;
    }

    public void onClickMine(View v){
        Query query= db.collection("users/"+signInAccount.getUid()+"/itineraries").whereEqualTo("prublisher",false);
        this.initializeItinerary(query);
        Toast.makeText(root.getContext(), "MIS ITINERARIOS GUARDADOS", Toast.LENGTH_SHORT).show();
    }

    public void onClickOther(View v){
        Query query= db.collection("users/"+signInAccount.getUid()+"/saved");
        this.initializeItinerary(query);
        Toast.makeText(root.getContext(), "ITINERARIOS DE OTROS GUARDADOS", Toast.LENGTH_SHORT).show();
    }

    private void initializeItinerary(Query query) {
        FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mAdapter = new Adapter(firestoreRecyclerOptions, root.getContext());
        mAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mAdapter);
        mAdapter.startListening();
    }


}