package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OnBoarding extends AppCompatActivity {

    Button next2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        next2 = findViewById(R.id.next2);
        auth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth instance
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            // User is logged in, navigate to the home page
            Intent intent = new Intent(OnBoarding.this, CommonPage.class); // Replace HomePage with your home activity
            startActivity(intent);
            finish();
        } else {
            // User is not logged in, show onboarding screen
            next2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create Intent to move to the SignUp activity
                    Intent intent = new Intent(OnBoarding.this, SignUpActivity.class);

                    // Start the SignUp activity
                    startActivity(intent);
                }
            });
        }
    }
}
