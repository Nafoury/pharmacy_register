package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePage extends AppCompatActivity {

    FloatingActionButton addB;

    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cardView1=findViewById(R.id.vitamins);
        cardView2=findViewById(R.id.hermon);
        cardView3=findViewById(R.id.supp);
        cardView4=findViewById(R.id.skin);
        cardView5=findViewById(R.id.skin2);
        cardView6=findViewById(R.id.vitamins1);

        addB=findViewById(R.id.add_button);

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the OnBoarding activity
                Intent intent = new Intent(HomePage.this, AddPage.class);

                // Start the OnBoarding activity
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Vitamins");
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Hormones");
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Skin Care");
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openCategoryDetail("Antipyretic");
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openCategoryDetail("Analgesics");
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Anxiety");
            }
        });


    }
    private void openCategoryDetail(String category) {
        // Open CategoryDetailActivity and pass the selected category name
        Intent intent = new Intent(HomePage.this, CategoryDisplayData.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}