package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button next1;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next1 = findViewById(R.id.next);
        auth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth instance
        FirebaseUser currentUser = auth.getCurrentUser();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (currentUser != null) {
            // User is logged in, navigate to the home page
            Intent intent = new Intent(MainActivity.this, CommonPage.class); // Replace HomePage with your home activity
            startActivity(intent);
            finish();
        } else {
            // User is not logged in, show onboarding screen
            next1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, OnBoarding.class);
                    startActivity(intent);
                }
            });
        }
    }
}
