package com.example.pharamacydrugregisteration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryDisplayData extends AppCompatActivity {

    RecyclerView recyclerView;
    DrugAdapter drugAdapter;
    DbSchemaSqlite dbSchemaSqlite;
    ArrayList<byte[]> drug_image;
    ArrayList<String> drug_name, drug_amount,drug_price,drug_category;

    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_display_data);


        recyclerView = findViewById(R.id.recycler_view);
        dbSchemaSqlite = new DbSchemaSqlite(CategoryDisplayData.this);
        drug_image = new ArrayList<>();
        drug_name = new ArrayList<>();
        drug_amount = new ArrayList<>();
        drug_price=new ArrayList<>();
        drug_category=new ArrayList<>();

        // Display data
        displayData();

        // Set up RecyclerView and adapter
        drugAdapter = new DrugAdapter(this, drug_name, drug_image, drug_amount,drug_price,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(drugAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void displayData() {
        Cursor cursor = dbSchemaSqlite.getDataByCategory(getIntent().getStringExtra("category"));
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            // Log the count of retrieved data
            Log.d("CursorCount", "No data retrieved for category: " + getIntent().getStringExtra("category"));
        } else {
            while (cursor.moveToNext()) {

                drug_name.add(cursor.getString(1));
                drug_amount.add(cursor.getString(2));
                drug_price.add(cursor.getString(3));
                drug_image.add(cursor.getBlob(4));
            }
            // Log the count of retrieved data
            Log.d("CursorCount", "Data retrieved for category: " + getIntent().getStringExtra("category") + ", Count: " + cursor.getCount());
        }
    }
}