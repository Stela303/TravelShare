package com.example.travelshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.travelshare.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;

public class GuestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AuthUI.getInstance().signOut(this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}