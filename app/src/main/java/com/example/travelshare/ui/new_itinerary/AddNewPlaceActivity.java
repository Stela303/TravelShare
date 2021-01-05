package com.example.travelshare.ui.new_itinerary;

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
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;

public class AddNewPlaceActivity extends AddContentNewItineraryActivity {


    Spinner topicSpinner;
    EditText priceEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SingletonMap.getInstance().containsKey(Constant.ITINERARY_KEY)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_new_place);
            super.itinerary = (Itinerary) SingletonMap.getInstance().get(Constant.ITINERARY_KEY);
            initializeVariables();
            initializeButtons();
        }
    }

    private void initializeVariables() {
        super.initializeVariables(R.id.namePlacetxt, R.id.locationPlacetxt, R.id.infoExtraPlacetxt,R.id.rv_images,this);
        this.priceEditText = (EditText) findViewById(R.id.pricePlacetxt);
        this.topicSpinner = (Spinner) findViewById(R.id.topicPlaceSpinner);
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
        return super.checkRequiredFields() && !priceEditText.getText().toString().equals("") && topicSpinner.getSelectedItem().toString().equals("");
    }

    protected void clearText() {
        super.clearText();
        this.priceEditText.setText("");
        //falta limpiar categorías
    }

    private void addPlaceOfInterest() {
        InterestingPlace interestingPlace = new InterestingPlace();
        interestingPlace.setName(super.nameEditText.getText().toString());
        interestingPlace.setLocation(super.locationEditText.getText().toString());
        interestingPlace.setPrice((Double.parseDouble(this.priceEditText.getText().toString())));
        this.itinerary.addInterestingPlace(interestingPlace);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }

    private void showDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
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