package com.example.travelshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.travelshare.ui.Itinerary;
import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GuestMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerViewItinerary;
    Adapter mAdapter;
    FirebaseFirestore db;
    SearchView search;
    FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        recyclerViewItinerary = findViewById(R.id.listItinerariesGues);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        Query query= db.collection("itineraries");
        //Query query=mFirestore.document("users/itineraries");
        this.initializeItinerary(query);

        search = findViewById(R.id.searchGues);
        search.setOnQueryTextListener(this);
    }

    private void initializeItinerary(Query query) {
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mAdapter = new Adapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AuthUI.getInstance().signOut(this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Query query= db.collection("users/itineraries").whereEqualTo("tittle", newText);
        // mAdapter.filter(newText);
        if(newText.length()==0){
            mAdapter.updateOptions(this.firestoreRecyclerOptions);
        }else{
            Query query= db.collection("itineraries").orderBy("tittle").startAt(newText)
                    .endAt(newText+"\uf8ff");
            FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                    .setQuery(query, Itinerary.class).build();
            mAdapter.updateOptions(firestoreRecyclerOptions);
        }

        return false;
    }
}