package com.example.travelshare.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.adapter.recycler.ItineraryAdapter;
import com.example.travelshare.ItineraryActivity;
import com.example.travelshare.R;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.data.model.Itinerary;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    View root;
    RecyclerView recyclerViewItinerary;
    ItineraryAdapter mItineraryAdapter;
    FirebaseFirestore db;
    SearchView search;
    FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerViewItinerary = root.findViewById(R.id.listItineraries);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(root.getContext()));
        db = FirebaseFirestore.getInstance();
        Query query= db.collectionGroup("itineraries").whereEqualTo("published", true);
        this.initializeItinerary(query);

        search = root.findViewById(R.id.search);
        search.setOnQueryTextListener(this);

        return root;
    }

    private void initializeItinerary(Query query) {
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mItineraryAdapter = new ItineraryAdapter(firestoreRecyclerOptions, root.getContext());
        mItineraryAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mItineraryAdapter);
        mItineraryAdapter.startListening();
        mItineraryAdapter.setOnItemClickListener(new ItineraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("ItineraryDocument", documentSnapshot);
                Intent intent= new Intent(root.getContext(), ItineraryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mItineraryAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        mItineraryAdapter.stopListening();
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