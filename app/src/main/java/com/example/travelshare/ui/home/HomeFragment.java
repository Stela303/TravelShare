package com.example.travelshare.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelshare.Adapter;
import com.example.travelshare.MainActivity;
import com.example.travelshare.R;
import com.example.travelshare.ui.Itinerary;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recyclerViewItinerary;
    Adapter mAdapter;
    FirebaseFirestore mFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        recyclerViewItinerary = root.findViewById(R.id.listItineraries);
        recyclerViewItinerary.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mFirestore = FirebaseFirestore.getInstance();
        Query query= mFirestore.collection("itineraries");
        FirestoreRecyclerOptions<Itinerary> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Itinerary>()
                .setQuery(query, Itinerary.class).build();
        mAdapter = new Adapter(firestoreRecyclerOptions, root.getContext());
        mAdapter.notifyDataSetChanged();
        recyclerViewItinerary.setAdapter(mAdapter);
        mAdapter.startListening();
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    @Override
    public void onStop(){
        super.onStop();
        mAdapter.stopListening();
    }
}