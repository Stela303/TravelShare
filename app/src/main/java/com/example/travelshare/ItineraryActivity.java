package com.example.travelshare;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelshare.adapter.FoodAdapter;
import com.example.travelshare.adapter.ItineraryAdapter;
import com.example.travelshare.adapter.PlaceAdapter;
import com.example.travelshare.adapter.StayAdapter;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.Stay;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.ui.home.HomeFragment;
import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ItineraryActivity extends AppCompatActivity {

    TextView tittle;
    TextView date;
    TextView location;
    TextView topic;
    TextView info;
    DocumentSnapshot itinerary;
    RecyclerView places;
    RecyclerView food;
    RecyclerView stays;
    FirebaseFirestore db;
    String userId;
    private FirebaseUser signInAccount;
    private PlaceAdapter mPlacesAdapter;
    private FoodAdapter mFoodAdapter;
    private StayAdapter mStayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();

        signInAccount = FirebaseAuth.getInstance().getCurrentUser();
        this.initVariables();
        this.initSaveButton();
        this.initPlaces();
        this.initFood();
        this.initStay();



    }

    private void initStay() {
        Query query= itinerary.getReference().collection("stays");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Stay>()
                .setQuery(query, Stay.class).build();
        mStayAdapter = new StayAdapter(firestoreRecyclerOptions, getApplicationContext());
        mStayAdapter.notifyDataSetChanged();
        stays.setAdapter(mStayAdapter);
        mStayAdapter.startListening();
        mStayAdapter.setOnItemClickListener(new StayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
            }
        });
    }

    private void initFood() {
        Query query= itinerary.getReference().collection("foodPlaces");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FoodPlace>()
                .setQuery(query, FoodPlace.class).build();
        mFoodAdapter = new FoodAdapter(firestoreRecyclerOptions, getApplicationContext());
        mFoodAdapter.notifyDataSetChanged();
        food.setAdapter(mFoodAdapter);
        mFoodAdapter.startListening();
        mFoodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
            }
        });
    }

    private void initPlaces() {
        Query query= itinerary.getReference().collection("interestingPlaces");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<InterestingPlace>()
                .setQuery(query, InterestingPlace.class).build();
        mPlacesAdapter = new PlaceAdapter(firestoreRecyclerOptions, getApplicationContext());
        mPlacesAdapter.notifyDataSetChanged();
        places.setAdapter(mPlacesAdapter);
        mPlacesAdapter.startListening();
        mPlacesAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
            }
        });
    }

    private void initSaveButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if(SingletonMap.getInstance().get("Saved")!=null && !(boolean)SingletonMap.getInstance().get("Saved")){
            fab.setVisibility(View.INVISIBLE);
        }else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Save itinerary in saved
                    db.collection("users").whereEqualTo("googleID", Objects.requireNonNull(signInAccount).getUid()).get()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    DocumentSnapshot user = task1.getResult().getDocuments().get(0);
                                    String location = (String) user.get("location");
                                    userId = (String) user.getId();
                                    db.collection("users").document(userId).collection("saved").add(itinerary.getData());
                                    Toast.makeText(getApplicationContext(), R.string.saved_itinerary, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }
        SingletonMap.getInstance().remove("Saved");
    }


    private void initVariables() {
        tittle = findViewById(R.id.tittleItinerary);
        date = findViewById(R.id.date);
        location = findViewById(R.id.locationItinerary);
        topic = findViewById(R.id.topicItinerary);
        info = findViewById(R.id.info);
        places = findViewById(R.id.placesItinerary);
        places.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        food = findViewById(R.id.foodItinerary);
        food.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stays = findViewById(R.id.staysItinerary);
        stays.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itinerary= (DocumentSnapshot) SingletonMap.getInstance().get("ItineraryDocument");
        SingletonMap.getInstance().remove("ItineraryDocument");
        tittle.setText((String) itinerary.get("name"));
        Date date_publishier=((Timestamp)itinerary.get("date_publishier")).toDate();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(dateFormat.format(date_publishier));
        location.setText((String) itinerary.get("location"));
        topic.setText((String) itinerary.get("topic"));
        info.setText((String) itinerary.get("extraInfo"));

    }
}