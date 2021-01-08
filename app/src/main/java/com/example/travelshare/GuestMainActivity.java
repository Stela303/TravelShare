package com.example.travelshare;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.travelshare.adapter.recycler.ItineraryAdapter;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GuestMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerViewItinerary;
    ItineraryAdapter mItineraryAdapter;
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
        Query query= db.collectionGroup("itineraries").whereEqualTo("published", true);
        this.initializeItinerary(query);

        search = findViewById(R.id.searchGues);
        search.setOnQueryTextListener(this);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AuthUI.getInstance().signOut(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);


    }

    private void initializeItinerary(Query query) {
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mItineraryAdapter = new ItineraryAdapter(firestoreRecyclerOptions, this);
        mItineraryAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mItineraryAdapter);
        mItineraryAdapter.startListening();
        mItineraryAdapter.setOnItemClickListener(new ItineraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("ItineraryDocument", documentSnapshot);
                SingletonMap.getInstance().put("Saved", false);
                Intent intent= new Intent(getApplicationContext(), ItineraryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AuthUI.getInstance().signOut(this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return this.onQueryTextChange(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()==0){
            mItineraryAdapter.updateOptions(this.firestoreRecyclerOptions);
        }else{
            Query query= db.collectionGroup("itineraries").whereEqualTo("published", true).orderBy("location").startAt(newText)
                    .endAt(newText+"\uf8ff");
            FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                    .setQuery(query, Itinerary.class).build();
            mItineraryAdapter.updateOptions(firestoreRecyclerOptions);
        }

        return false;
    }
}