package com.example.travelshare.ui.new_itinerary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelshare.R;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.repository.ItineraryRepository;
import com.example.travelshare.repository.TopicRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewItineraryActivity extends AppCompatActivity {


    private Itinerary itinerary;


    EditText nameEditText;
    EditText locationEditText;
    Spinner topicSpinner;
    EditText commentsEditText;
    List<String> topics=new ArrayList<>();
    TopicRepository topicRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_itinerary);
        this.itinerary = new Itinerary();
        initializeVariables();
        initializeButtons();

    }


    private void addPlace() {
        Button btn = (Button) findViewById(R.id.btn_add_place);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(AddNewPlaceActivity.class, v);
            }
        });
    }

    private void addStay() {
        Button btn = (Button) findViewById(R.id.btn_add_stay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(AddNewStayActivity.class, v);
            }
        });
    }

    private void addFood() {
        Button btn = (Button) findViewById(R.id.btn_add_food);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(AddFoodActivity.class, v);
            }
        });
    }

    private void save() {
        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent(v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void share() {
        Button btn = (Button) findViewById(R.id.btn_share_friends);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent(v.getContext(), AddNewPlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void post() {
        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checKContent()) {
                    updateValues();
                    itinerary.setDate_publishier(new Date());
                    ItineraryRepository.getInstance().create(itinerary,getApplicationContext());
                } else {
                    Toast contentNecessaryToast = Toast.makeText(getApplicationContext(), R.string.content_itinerary_necessary_message, Toast.LENGTH_SHORT);
                    contentNecessaryToast.show();
                }

            }
        });
    }

    private boolean checKContent() {
        return !(itinerary.getFoodPlaces().isEmpty()
                && itinerary.getInterestingPlaces().isEmpty()
                && itinerary.getStays().isEmpty());
    }

    private void updateValues() {
        itinerary.setName(nameEditText.getText().toString());
        itinerary.setLocation(locationEditText.getText().toString());
    //    itinerary.setTopic(topicSpinner.getSelectedItem().toString());
        itinerary.setExtraInfo(commentsEditText.getText().toString());
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }

    private void initializeVariables() {
        nameEditText = findViewById(R.id.namePlacetxt);
        locationEditText = findViewById(R.id.locationPlacetxt);
        topicSpinner = findViewById(R.id.spinnerTopic);
        commentsEditText = findViewById(R.id.editTextMultilineComments);
        topics.add("");
      //  this.topicRepository= (TopicRepository) SingletonMap.getInstance().get("topicRepository");
      //  this.topicRepository.searchAllTopics(new getAllTopicsOnCompleteListener());

    }

    private void nextView(Class<?> cls, View view) {
        if (checkRequiredFields()) {
            updateValues();
            Intent intent = new Intent(view.getContext(), AddNewPlaceActivity.class);
            startActivityForResult(intent, 0);
        } else {
            Toast requiredFieldsToast = Toast.makeText(getApplicationContext(), R.string.fields_itinerary_required_message, Toast.LENGTH_SHORT);
            requiredFieldsToast.show();
        }
    }

    private void initializeButtons() {
        addFood();
        addPlace();
        addStay();
        post();
        save();
        share();
    }

    private boolean checkRequiredFields() {
        return !(nameEditText.getText().toString().equals("")
//                && topicSpinner.getSelectedItem().toString().equals("")
                && locationEditText.getText().toString().equals("")
                && commentsEditText.getText().toString().equals(""));
    }

    private class getAllTopicsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        private static final String TAG = "";

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        topics.add(document.getString(getString(R.string.languague_code)));
                    }
                    topicSpinner.setAdapter((SpinnerAdapter) topics);
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }
        }
    }
}