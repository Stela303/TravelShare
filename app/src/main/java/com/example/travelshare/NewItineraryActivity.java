package com.example.travelshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewItineraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_itinerary);
        addPlace();

    }

    private void addPlace(){
        Button btn = (Button) findViewById(R.id.btn_add_place);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}