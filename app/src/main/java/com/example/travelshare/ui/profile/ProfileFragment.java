package com.example.travelshare.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelshare.R;
import com.example.travelshare.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    String userId;
    TextView name;
    TextView email;
    EditText location;
    ImageView photo;
    Button save;
    ImageButton edit;
    View root;

    private FirebaseUser signInAccount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        name = root.findViewById(R.id.nameProfile);
        email = root.findViewById(R.id.emailProfile);
        location = root.findViewById(R.id.locationProfile);
        photo = root.findViewById(R.id.imageProfile);
        save = root.findViewById(R.id.saveProfile);
        edit = root.findViewById(R.id.editProfile);

        edit.setOnClickListener(v -> {
            location.setText("");
            location.setEnabled(true);
            save.setEnabled(true);
        });

        save.setOnClickListener(v -> {
            Toast.makeText(root.getContext(), "Guardar localizacion", Toast.LENGTH_SHORT).show();
            System.out.println(userId);
            usersRef.document(userId).update("location", location.getText().toString());
            location.setEnabled(false);
            save.setEnabled(false);
        });


        signInAccount = FirebaseAuth.getInstance().getCurrentUser();
        //signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!= null){
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
            Picasso.with(root.getContext()).load(signInAccount.getPhotoUrl()).into(photo);
            usersRef.whereEqualTo("email", Objects.requireNonNull(signInAccount).getEmail()).get()
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()){
                            DocumentSnapshot user= task1.getResult().getDocuments().get(0);
                            String location = (String) user.get("location");
                            userId = (String) user.getId();
                            if(location!=null){
                                this.location.setText(location);
                            }
                        }
                    });
        }else{
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        }


        return root;
    }
}