package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnBoarding extends AppCompatActivity {

    Button next2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        next2=findViewById(R.id.next2);

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the OnBoarding activity
                Intent intent = new Intent(OnBoarding.this, HomePage.class);

                // Start the OnBoarding activity
                startActivity(intent);
            }
        });

    }
}