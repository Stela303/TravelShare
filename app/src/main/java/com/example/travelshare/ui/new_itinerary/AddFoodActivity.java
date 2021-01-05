package com.example.travelshare.ui.new_itinerary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.travelshare.R;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.repository.TopicRepository;

import java.util.List;

public class AddFoodActivity extends AddContentNewItineraryActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        super.itinerary = (Itinerary) SingletonMap.getInstance().get(Constant.ITINERARY_KEY);
        initializeVariables();
        initializeButtons();
    }

    protected void initializeVariables(){

    }

    protected void initializeButtons(){

    }

    private void addFood() {

       // private String name;
       // private String location;
       // private String type;
       // private String category;
       // private double averagePrice;
       // private String  extraInfo;
        //private List<String> images;

        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setName(super.nameEditText.getText().toString());
        foodPlace.setLocation(super.locationEditText.getText().toString());
        foodPlace.setExtraInfo(super.infoExtraEditText.getText().toString());
        this.itinerary.addFoodPlace(foodPlace);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }
}