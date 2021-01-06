package com.example.travelshare.ui.new_itinerary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        super.initializeVariables(R.id.nameFoodTxt, R.id.txtFoodLocation,R.id.infoExtraFoodTxt,R.id.rv_images,this);
    }

    protected void initializeButtons(){
        super.initializeButtons(R.id.btnAddImagePlace);
        Button btnSave = (Button) findViewById(R.id.btnSavePlace);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (checkRequiredFields()) {
                                               addFood();
                                               showDialog(v);
                                           } else {
                                               Toast requiredFieldsToast = Toast.makeText(getApplicationContext(), R.string.fields_itinerary_required_message, Toast.LENGTH_SHORT);
                                               requiredFieldsToast.show();
                                           }
                                       }
                                   }
        );
    }

    private void addFood() {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setName(super.nameEditText.getText().toString());
        foodPlace.setLocation(super.locationEditText.getText().toString());
        foodPlace.setExtraInfo(super.infoExtraEditText.getText().toString());
        this.itinerary.addFoodPlace(foodPlace);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }

    private void showDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setTitle(R.string.food_added);
        alertDialogBuilder
                .setMessage(R.string.do_you_want_add_more_food)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearText();
                        dialog.cancel();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(view.getContext(), NewItineraryActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }).create().show();
    }
}