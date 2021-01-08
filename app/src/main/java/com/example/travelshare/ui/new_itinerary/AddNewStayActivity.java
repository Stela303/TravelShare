package com.example.travelshare.ui.new_itinerary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.travelshare.R;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.Stay;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddNewStayActivity extends AddContentNewItineraryActivity {

    EditText priceNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_new_stay);
        initializeVariables();
        initializeButtons();

    }

    private void initializeVariables() {
        super.initializeVariables(R.id.nameStaytxt, R.id.locationStaytxt, R.id.infoExtraStaytxt,R.id.recyclerViewStay,this);
        this.priceNight = (EditText) findViewById(R.id.priceNigthStaytxt);

    }

    private void initializeButtons() {
        super.initializeButtons(R.id.btnAddImageStay);
        Button btnSave = (Button) findViewById(R.id.btnSaveStay);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (checkRequiredFields()) {
                                               addStay();
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
        return super.checkRequiredFields() && !priceNight.getText().toString().equals("") ;
    }

    protected void clearText() {
        super.clearText();
        this.priceNight.setText("");
    }

    private void addStay() {
        Stay stay = new Stay();
        stay.setName(super.nameEditText.getText().toString());
        stay.setLocation(super.locationEditText.getText().toString());
        stay.setPriceNight((Double.parseDouble(this.priceNight.getText().toString())));
        stay.setImages(super.urls);
        if(!super.infoExtraEditText.getText().toString().equals("")){
            stay.setExtraInfo(super.infoExtraEditText.getText().toString());
        }
        this.itinerary.addStay(stay);
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }

    private void showDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setTitle(R.string.stay_added);
        alertDialogBuilder
                .setMessage(R.string.do_you_want_add_more_stay)
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