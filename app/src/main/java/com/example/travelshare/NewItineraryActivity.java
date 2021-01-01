package com.example.travelshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.travelshare.data.model.Itinerary;

import java.util.HashMap;

public class NewItineraryActivity extends AppCompatActivity {

    private Itinerary itinerary;

    EditText nameEditText;
    EditText locationEditText;
    Spinner topicSpinner;
    EditText commentsEditText;



    //Keys
    private final String NAME="name";
    private final String LOCATION="location";
    private final String TOPIC="topic";
    private final String COMMENTS="comments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_itinerary);
        itinerary=new Itinerary();
        initializeButtons();
        initializeVariables();
    }

    private void addPlace(){
        Button btn = (Button) findViewById(R.id.btn_add_place);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void addStay(){
        Button btn = (Button) findViewById(R.id.btn_add_stay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void addFood(){
        Button btn = (Button) findViewById(R.id.btn_add_food);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddFoodActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void save(){
        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void share(){
        Button btn = (Button) findViewById(R.id.btn_share_friends);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void post(){
        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void updateValues(){
        if(!nameEditText.getText().toString().equals("")){
            itinerary.setName(nameEditText.getText().toString());
        }
        if(!locationEditText.getText().toString().equals("")){
            itinerary.setLocation(locationEditText.getText().toString());
        }
        if(!topicSpinner.getSelectedItem().toString().equals("")){
            itinerary.setTopic(topicSpinner.getSelectedItem().toString());
        }
        if(!commentsEditText.getText().toString().equals("")){
            itinerary.setExtraInfo(commentsEditText.getText().toString());
        }
    }

    private void initializeButtons(){
        addPlace();
        addStay();
        addFood();
        save();
        share();
        post();
    }

    private void initializeVariables(){
         nameEditText = (EditText) findViewById(R.id.editTextName);
         locationEditText = (EditText) findViewById(R.id.editTextLocation);
         topicSpinner = (Spinner) findViewById(R.id.spinnerTopic);
         commentsEditText = (EditText) findViewById(R.id.editTextMultilineComments);
    }

}