package com.example.travelshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.travelshare.adapter.recycler.list.FoodAdapter;
import com.example.travelshare.adapter.recycler.list.PlaceAdapter;
import com.example.travelshare.adapter.recycler.list.StayAdapter;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.Stay;
import com.example.travelshare.library.SingletonMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ItineraryActivity extends AppCompatActivity implements
        FoodAdapter.OnFoodListener,
        PlaceAdapter.OnPlaceListener,
        StayAdapter.OnStayListener{

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

    RecyclerView places;
    RecyclerView food;
    RecyclerView stays;

    List<FoodPlace> foodItinerary;
    List<InterestingPlace> placeItinerary;
    List<Stay> stayItinerary;

    FirebaseFirestore db;
    String userId;
    private FirebaseUser signInAccount;
    private PlaceAdapter mPlaceAdapter;
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


    }

    private void initStay() {

        stayItinerary=itineraryObject.getStays();
        if(stays!=null){
            mStayAdapter = new StayAdapter(getApplicationContext(), stayItinerary, this);
            this.stays.setAdapter(mStayAdapter);
        }else{
            linearStay.setVisibility(View.INVISIBLE);
            dividerStay.setVisibility(View.INVISIBLE);
        }
    }

    private void initFood() {

        foodItinerary=itineraryObject.getFoodPlaces();
        if(foodItinerary!=null){
            mFoodAdapter = new FoodAdapter(getApplicationContext(), foodItinerary, this);
            this.food.setAdapter(mFoodAdapter);
        }else{
            linearFood.setVisibility(View.INVISIBLE);
        }
    }

    private void initPlaces() {
        placeItinerary=itineraryObject.getInterestingPlaces();
        if(placeItinerary!=null){
            mPlaceAdapter = new PlaceAdapter(getApplicationContext(), placeItinerary, this);
            this.places.setAdapter(mPlaceAdapter);
        }else{
            linearPlaces.setVisibility(View.INVISIBLE);
            dividerPlaces.setVisibility(View.INVISIBLE);
        }
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

        places = findViewById(R.id.placesItinerary);
        places.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        food=findViewById(R.id.foodItinerary);
        food.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stays = findViewById(R.id.staysItinerary);
        stays.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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

    @Override
    public void onFoodClick(int position) {
        SingletonMap.getInstance().put("Images", foodItinerary.get(position).getImages());
        SingletonMap.getInstance().put("Location", foodItinerary.get(position).getLocation());
        startAplication();
    }

    private void startAplication() {
        Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onPlaceClick(int position) {
        SingletonMap.getInstance().put("Images", placeItinerary.get(position).getImages());
        SingletonMap.getInstance().put("Location", placeItinerary.get(position).getLocation());
        startAplication();
    }

    @Override
    public void onStayClick(int position) {
        SingletonMap.getInstance().put("Images", stayItinerary.get(position).getImages());
        SingletonMap.getInstance().put("Location", stayItinerary.get(position).getLocation());
        startAplication();
    }
}