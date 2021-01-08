package com.example.travelshare.ui.new_itinerary;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travelshare.MainActivity;
import com.example.travelshare.R;
import com.example.travelshare.library.CloudStorage;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.repository.ItineraryRepository;
import com.example.travelshare.repository.TopicRepository;
import com.example.travelshare.ui.adapter.ImageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class NewItineraryActivity extends AppCompatActivity {


        private Itinerary itinerary;
        private static final int GALLERY_REQUEST = 2200 ;
        private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        private GalleryPhoto galleryPhoto;
        private ArrayList<String> topics;

        EditText nameEditText;
        EditText locationEditText;
        Spinner topicSpinner;
        EditText commentsEditText;
        ImageView coverPhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_itinerary);
        initializeVariables();
        initializeButtons();
        if(SingletonMap.getInstance().containsKey(Constant.ITINERARY_KEY)){
            this.itinerary=(Itinerary)SingletonMap.getInstance().get(Constant.ITINERARY_KEY);
            fillFields();
        }else{
            this.itinerary = new Itinerary();
        }

    }


    private void fillFields() {
        if(!this.itinerary.getExtraInfo().equals("")){
            commentsEditText.setText(this.itinerary.getExtraInfo());
        }
        this.nameEditText.setText(itinerary.getName());
        this.locationEditText.setText(itinerary.getLocation());
        int index=topics.indexOf(this.itinerary.getTopic());
        topicSpinner.setSelection(index);
        Bitmap bitmap= null;
        try {
            bitmap = ImageLoader.init().from(itinerary.getImage()).getBitmap();
            this.coverPhoto.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                if (checkRequiredFields()) {
                    updateValues();
                    ItineraryRepository.getInstance().create(itinerary,getApplicationContext());
                    nextView(MainActivity.class, v);
                } else {
                    Toast requiredFieldsToast = Toast.makeText(getApplicationContext(), R.string.fields_itinerary_required_message, Toast.LENGTH_SHORT);
                    requiredFieldsToast.show();
                }
            }
        });
    }

    private void share() {
        Button btn = (Button) findViewById(R.id.btn_share_friends);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                Intent intent = new Intent(v.getContext(), ShareFriendsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void post() {
        Button btn = (Button) findViewById(R.id.btn_post);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checKContent()) {
                    updateValues();
                    itinerary.setDate_publishier(new Date());
                    itinerary.setPublished(true);
                    ItineraryRepository.getInstance().create(itinerary,getApplicationContext());
                    nextView(MainActivity.class, v);
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
                && itinerary.getStays().isEmpty()
        );
    }

    private void updateValues() {
        itinerary.setName(nameEditText.getText().toString());
        itinerary.setLocation(locationEditText.getText().toString());
        System.out.println(topicSpinner.getSelectedItem());
        itinerary.setTopic(topicSpinner.getSelectedItem().toString());
        System.out.println(itinerary.getTopic());
        itinerary.setExtraInfo(commentsEditText.getText().toString());
        SingletonMap.getInstance().put(Constant.ITINERARY_KEY, this.itinerary);
    }


    private void initializeVariables() {
        nameEditText = findViewById(R.id.namePlacetxt);
        locationEditText = findViewById(R.id.locationPlacetxt);
        topicSpinner = findViewById(R.id.spinnerTopic);
        commentsEditText = findViewById(R.id.editTextMultilineComments);
        coverPhoto = findViewById(R.id.coverPhotoImageView);
        TopicRepository topicRepository = new TopicRepository();
        this.galleryPhoto= new GalleryPhoto(getApplicationContext());
        this.topics=new ArrayList<>();
        if( SingletonMap.getInstance().containsKey(Constant.TOPICS)){
            this.topics.addAll((List<String>)SingletonMap.getInstance().get(Constant.TOPICS));
            ArrayAdapter<String> topicArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item, topics);
            topicSpinner.setAdapter((SpinnerAdapter) topicArrayAdapter);
        }else{
            topics.add("");
            topicRepository.searchAllTopics(new getAllTopicsOnCompleteListener());
        }
    }

    private void nextView(Class<?> cls, View view) {
        if (checkRequiredFields()) {
            updateValues();
            Intent intent = new Intent(view.getContext(),cls);
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
        addCoverPhoto();
    }

    private void addCoverPhoto() {
        Button btn = (Button) findViewById(R.id.btnAddCoverPhoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
    }

    private boolean checkRequiredFields() {
        return !(nameEditText.getText().toString().equals("")
                && topicSpinner.getSelectedItem().toString().equals("")
                && locationEditText.getText().toString().equals("")
                && commentsEditText.getText().toString().equals("")
                && this.itinerary.getImage()==null
        );
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
                    ArrayAdapter<String> topicArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item, topics);
                    topicSpinner.setAdapter((SpinnerAdapter) topicArrayAdapter);
                    SingletonMap.getInstance().put(Constant.TOPICS,topics);
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                    this.galleryPhoto.setPhotoUri(data.getData());
                    String photoPath = this.galleryPhoto.getPath();
                    this.itinerary.setImage(photoPath);
                    Bitmap bitmap = null;
                    try {
                        bitmap = ImageLoader.init().from(photoPath).getBitmap();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    this.coverPhoto.setImageBitmap(bitmap);
                } else {
                    EasyPermissions.requestPermissions(this, "Access for storage",
                            101, galleryPermissions);
                }
            }
        }
    }
}