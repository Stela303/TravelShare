package com.example.travelshare.ui.new_itinerary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.travelshare.R;
import com.example.travelshare.data.model.FoodPlace;
import com.example.travelshare.data.model.InterestingPlace;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.repository.CategoryRepository;
import com.example.travelshare.repository.TopicRepository;
import com.example.travelshare.repository.TypeRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AddContentNewItineraryActivity {

    Spinner typesSpinner;
    Spinner categoriesSpinner;
    EditText averagePrice;
    ArrayList<String> types;
    ArrayAdapter<String> typesArrayAdapter;
    TypeRepository typeRepository;
    ArrayList<String> categories;
    ArrayAdapter<String> categoriesArrayAdapter;
    CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        super.itinerary = (Itinerary) SingletonMap.getInstance().get(Constant.ITINERARY_KEY);
        initializeVariables();
        initializeButtons();
    }

    protected void initializeVariables(){
        super.initializeVariables(R.id.nameFoodTxt, R.id.txtFoodLocation,R.id.infoExtraFoodTxt,R.id.recyclerviewFood,this);
        typesSpinner= (Spinner) findViewById(R.id.typeFoodSpinner);
        categoriesSpinner =(Spinner) findViewById(R.id.categoryFoodSpinner);
        averagePrice=(EditText)findViewById(R.id.averagePriceFoodtxt) ;
        typeRepository=new TypeRepository();
        categoryRepository=new CategoryRepository();
        types=new ArrayList<>();
        types.add("");
        categories=new ArrayList<>();
        categories.add("");
        typeRepository.searchAllTypes(new getAllTypesOnCompleteListener());
        categoryRepository.searchAllCategories(new getAllCategoriesOnCompleteListener());
    }

    protected void initializeButtons(){
        super.initializeButtons(R.id.btnAddImageFood);
        Button btnSave = (Button) findViewById(R.id.btnSaveFood);
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
        foodPlace.setCategory(categoriesSpinner.getSelectedItem().toString());
        foodPlace.setType(typesSpinner.getSelectedItem().toString());
        foodPlace.setImages(super.urls);
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

    private class getAllTypesOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        private static final String TAG = "";

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        types.add(document.getString(getString(R.string.languague_code)));
                    }
                    typesArrayAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_simple_item,types);
                    typesSpinner.setAdapter((SpinnerAdapter) typesArrayAdapter);
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }
        }
    }

    private class getAllCategoriesOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        private static final String TAG = "";

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        categories.add(document.getString(getString(R.string.languague_code)));
                    }
                    categoriesArrayAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_simple_item,categories);
                    categoriesSpinner.setAdapter((SpinnerAdapter) categoriesArrayAdapter);
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }
        }
    }

    protected boolean checkRequiredFields(){
        return super.checkRequiredFields() && !typesSpinner.getSelectedItem().toString().equals("") && !categoriesSpinner.getSelectedItem().toString().equals("") && !averagePrice.getText().toString().equals("");
    }
}