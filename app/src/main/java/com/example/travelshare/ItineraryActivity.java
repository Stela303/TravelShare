package com.example.travelshare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.travelshare.adapter.list.FoodAdapter;
import com.example.travelshare.adapter.list.ImageAdapter;
import com.example.travelshare.adapter.list.PlaceAdapter;
import com.example.travelshare.adapter.list.StayAdapter;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.Stay;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.util.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ItineraryActivity extends AppCompatActivity {

    TextView tittle;
    TextView date;
    TextView location;
    TextView topic;
    TextView info;
    DocumentSnapshot itinerary;
    Itinerary itineraryObject;
    LinearLayout linearPlaces;
    LinearLayout linearFood;
    LinearLayout linearStay;
    View dividerPlaces;
    View dividerStay;
    ListView places;
    ListView food;
    ListView stays;
    /*
    RecyclerView places;
    RecyclerView food;
    RecyclerView stays;
     */
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
        this.initStay();
        this.initFood();
        Utility.setListViewHeightBasedOnChildren(this.places);
        Utility.setListViewHeightBasedOnChildren(this.stays);
        Utility.setListViewHeightBasedOnChildren(this.food);


    }

    private void initStay() {

        List<Stay> stays=itineraryObject.getStays();
        if(stays!=null){
            StayAdapter adapter = new StayAdapter(this, stays);
            this.stays.setAdapter(adapter);
            this.stays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SingletonMap.getInstance().put("Images", stays.get(position).getImages());
                    SingletonMap.getInstance().put("Location", stays.get(position).getLocation());
                    Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }else{
            linearStay.setVisibility(View.INVISIBLE);
            dividerStay.setVisibility(View.INVISIBLE);
        }

        /*
        Query query = itinerary.getReference().collection("stays");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Stay>()
                .setQuery(query, Stay.class).build();
        mStayAdapter = new StayAdapter(firestoreRecyclerOptions, getApplicationContext());
        mStayAdapter.notifyDataSetChanged();
        stays.setAdapter(mStayAdapter);
        mStayAdapter.startListening();
        mStayAdapter.setOnItemClickListener(new StayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("Document", documentSnapshot);
                Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                startActivityForResult(intent, 0);
            }
        });*/


    }

    private void initFood() {

        List<FoodPlace> food=itineraryObject.getFoodPlaces();
        if(food!=null){
            FoodAdapter adapter = new FoodAdapter(this, food);
            this.food.setAdapter(adapter);
            this.food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SingletonMap.getInstance().put("Images", food.get(position).getImages());
                    SingletonMap.getInstance().put("Location", food.get(position).getLocation());
                    Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }else{
            linearFood.setVisibility(View.INVISIBLE);
        }
        /*
        Query query = itinerary.getReference().collection("foodPlaces");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FoodPlace>()
                .setQuery(query, FoodPlace.class).build();
        mFoodAdapter = new FoodAdapter(firestoreRecyclerOptions, getApplicationContext());
        mFoodAdapter.notifyDataSetChanged();
        food.setAdapter(mFoodAdapter);
        mFoodAdapter.startListening();
        mFoodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("Document", documentSnapshot);
                Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

         */

    }

    private void initPlaces() {
        List<InterestingPlace> places=itineraryObject.getInterestingPlaces();
        if(places!=null){
            PlaceAdapter adapter = new PlaceAdapter(this, places);
            this.places.setAdapter(adapter);
            this.places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SingletonMap.getInstance().put("Images", places.get(position).getImages());
                    SingletonMap.getInstance().put("Location", places.get(position).getLocation());
                    Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }else{
            linearPlaces.setVisibility(View.INVISIBLE);
            dividerPlaces.setVisibility(View.INVISIBLE);
        }
        /*
        Query query = itinerary.getReference().collection("interestingPlaces");
        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<InterestingPlace>()
                .setQuery(query, InterestingPlace.class).build();
        mPlacesAdapter = new PlaceAdapter(firestoreRecyclerOptions, getApplicationContext());
        mPlacesAdapter.notifyDataSetChanged();
        places.setAdapter(mPlacesAdapter);
        mPlacesAdapter.startListening();
        mPlacesAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                SingletonMap.getInstance().put("Document", documentSnapshot);
                Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

         */

    }

    private void initSaveButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if (SingletonMap.getInstance().get("Saved") != null && !(boolean) SingletonMap.getInstance().get("Saved")) {
            fab.setVisibility(View.INVISIBLE);
        } else {
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
                                    //db.collection("users").document(userId).collection("saved").document(itinerary.getId()).collection("interestingPlaces").add(itinerary.)
                                    //db.collection("users").document(userId).collection("saved").document(itinerary.getId()).collection("foodPlaces")
                                    //db.collection("users").document(userId).collection("saved").document(itinerary.getId()).collection("stays")
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
        linearPlaces = findViewById(R.id.linearPlaces);
        linearFood = findViewById(R.id.linearFood);
        linearStay = findViewById(R.id.linearStay);
        dividerPlaces = findViewById(R.id.dividerPlaces);
        dividerStay = findViewById(R.id.dividerStay);
        places = (ListView) findViewById(R.id.placesItinerary);
        food = (ListView) findViewById(R.id.foodItinerary);
        stays = (ListView) findViewById(R.id.staysItinerary);
        /*
        places = findViewById(R.id.placesItinerary);
        places.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        food = findViewById(R.id.foodItinerary);
        food.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stays = findViewById(R.id.staysItinerary);
        stays.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
         */
        itinerary = (DocumentSnapshot) SingletonMap.getInstance().get("ItineraryDocument");
        SingletonMap.getInstance().remove("ItineraryDocument");
        itineraryObject = itinerary.toObject(Itinerary.class);
        tittle.setText((String) itinerary.get("name"));
        Object dateIt = itinerary.get("date_publishier");
        if (dateIt != null) {
            Date datePublishier = ((Timestamp) dateIt).toDate();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormat.format(datePublishier));
        }else{
            Object dateItSaved = itinerary.get("date_created");
            if (dateItSaved != null) {
                Date dateCreated = ((Timestamp) dateItSaved).toDate();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date.setText(dateFormat.format(dateCreated));
            }
        }
        location.setText((String) itinerary.get("location"));
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String map = "http://maps.google.com/maps?q=" + itinerary.get("location");
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(i);
            }
        });
        topic.setText((String) itinerary.get("topic"));
        info.setText((String) itinerary.get("extraInfo"));

    }
}