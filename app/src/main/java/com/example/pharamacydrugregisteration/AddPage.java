package com.example.pharamacydrugregisteration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class AddPage extends AppCompatActivity {
    Bitmap bitmap;
    EditText name_input, amount_input, price_input;
    Button add_button;
    ImageView imageView;
    Spinner spinnerCategory;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        name_input = findViewById(R.id.editTextText3);
        amount_input = findViewById(R.id.editTextNumberDecimal);
        price_input = findViewById(R.id.editTextNumberDecimal2);
        add_button = findViewById(R.id.savedrug);
        imageView = findViewById(R.id.imageView3);
        spinnerCategory = findViewById(R.id.spinner_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add drug");

            actionBar.setDisplayHomeAsUpEnabled(true); // Enable back arrow
        }

        // Gallery launcher for selecting image
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                    // Set the selected image to ImageView or do whatever you want with it
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        imageView.setOnClickListener(v -> openGallery());

        add_button.setOnClickListener(v -> {
            byte[] imageData = getBitmapAsByteArray(bitmap);
            insertDrugData(name_input.getText().toString().trim(),
                    Integer.parseInt(amount_input.getText().toString().trim()),
                    Float.parseFloat(price_input.getText().toString().trim()),
                    imageData,
                    spinnerCategory.getSelectedItem().toString().trim());
        });
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private void insertDrugData(String name, int amount, float price, byte[] image,String category) {
        DbSchemaSqlite db = new DbSchemaSqlite(this);
        long result = db.addDrug(name, amount, price, image,category);
        if (result == -1) {
            Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}