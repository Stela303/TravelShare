package com.example.travelshare.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.Adapter;
import com.example.travelshare.R;
import com.example.travelshare.ui.Itinerary;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private HomeViewModel homeViewModel;
    View root;
    RecyclerView recyclerViewItinerary;
    Adapter mAdapter;
    FirebaseFirestore db;
    SearchView search;
    FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerViewItinerary = root.findViewById(R.id.listItineraries);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(root.getContext()));
        db = FirebaseFirestore.getInstance();
        Query query= db.collection("itineraries");
        //Query query=db.document("users/itineraries").collection("itineraries");
        this.initializeItinerary(query);

        search = root.findViewById(R.id.search);
        search.setOnQueryTextListener(this);

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    private void initializeItinerary(Query query) {
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mAdapter = new Adapter(firestoreRecyclerOptions, root.getContext());
        mAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        mAdapter.stopListening();
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
            Query query= db.collection("itineraries").orderBy("location").startAt(newText)
                    .endAt(newText+"\uf8ff");
            FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                    .setQuery(query, Itinerary.class).build();
            mAdapter.updateOptions(firestoreRecyclerOptions);
        }

        return false;
    }
}