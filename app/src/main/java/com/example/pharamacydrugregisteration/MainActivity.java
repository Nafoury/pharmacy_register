package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button next1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next1 = findViewById(R.id.next);


        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the OnBoarding activity
                Intent intent = new Intent(MainActivity.this, OnBoarding.class);

                // Start the OnBoarding activity
                startActivity(intent);
            }
        });
    }
}
