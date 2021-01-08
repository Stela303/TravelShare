package com.example.travelshare.ui.new_itinerary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.travelshare.R;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;

import java.util.List;

public class AddNewPlaceActivity extends AddContentNewItineraryActivity {


    Spinner topicSpinner;
    EditText priceEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_add_new_place);
            initializeVariables();
            initializeButtons();
    }

    private void initializeVariables() {
        super.initializeVariables(R.id.namePlacetxt, R.id.locationPlacetxt, R.id.infoExtraPlacetxt,R.id.rv_images,this);
        this.priceEditText = (EditText) findViewById(R.id.pricePlacetxt);
        this.topicSpinner = (Spinner) findViewById(R.id.topicPlaceSpinner);
        ArrayAdapter<String> topicArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item, ((List<String>) SingletonMap.getInstance().get(Constant.TOPICS)));
        topicSpinner.setAdapter((SpinnerAdapter) topicArrayAdapter);
    }

    private void initializeButtons() {
        super.initializeButtons(R.id.btnAddImagePlace);
        Button btnSave = (Button) findViewById(R.id.btnSavePlace);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRequiredFields()) {
                    addPlaceOfInterest();
                    showDialog(v);
                } else {
                    Toast requiredFieldsToast = Toast.makeText(getApplicationContext(), R.string.fields_itinerary_required_message, Toast.LENGTH_SHORT);
                    requiredFieldsToast.show();
                }
            }
        }
        );
    }

    protected boolean checkRequiredFields() {
        return super.checkRequiredFields() && !priceEditText.getText().toString().equals("")
                     && !topicSpinner.getSelectedItem().toString().equals("");
    }

    protected void clearText() {
        super.clearText();
        this.priceEditText.setText("");
        this.topicSpinner.setSelection(0);
    }

    private void addPlaceOfInterest() {
        InterestingPlace interestingPlace = new InterestingPlace();
        interestingPlace.setName(super.nameEditText.getText().toString());
        interestingPlace.setLocation(super.locationEditText.getText().toString());
        interestingPlace.setPrice((Double.parseDouble(this.priceEditText.getText().toString())));
        interestingPlace.setTopic(topicSpinner.getSelectedItem().toString());
        interestingPlace.setImages(super.urls);
        this.itinerary.addInterestingPlace(interestingPlace);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }

    private void showDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setTitle(R.string.place_added);
        alertDialogBuilder
                .setMessage(R.string.do_you_want_add_more_places)
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