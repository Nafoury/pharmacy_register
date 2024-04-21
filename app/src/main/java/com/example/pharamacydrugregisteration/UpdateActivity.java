package com.example.pharamacydrugregisteration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {
    EditText name_input1, amount_input1, price_input1;
    Bitmap bitmap;
    Button update_button;
    ImageView imageView1;
    Spinner spinnerCategory1;
    String amount,price,name,category,id,image;
    private ActivityResultLauncher<String> galleryLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        name_input1=findViewById(R.id.drugnameu);
        amount_input1=findViewById(R.id.drugamountu);
        price_input1=findViewById(R.id.drugpriceu);
        update_button=findViewById(R.id.updatedrug);
        imageView1=findViewById(R.id.imageu);
        spinnerCategory1=findViewById(R.id.spinner_category1);

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                    // Set the selected image to ImageView or do whatever you want with it
                    imageView1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory1.setAdapter(adapter);

        imageView1.setOnClickListener(v -> openGallery());
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from UI components
                name = name_input1.getText().toString().trim();
                amount = amount_input1.getText().toString().trim();
                price = price_input1.getText().toString().trim();
                category = spinnerCategory1.getSelectedItem().toString().trim();

                // Convert bitmap to byte array
                byte[] newImage = getBitmapAsByteArray(bitmap);



                // Update data in database
                DbSchemaSqlite dbSchemaSqlite = new DbSchemaSqlite(UpdateActivity.this);
                dbSchemaSqlite.updateData(id,name,amount,price,newImage, category);
            }
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

    void getAndSetIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("amount") && getIntent().hasExtra("price") &&
                getIntent().hasExtra("category") && getIntent().hasExtra("image")){
            // Getting the data from Intent
            name = getIntent().getStringExtra("name");
            amount = getIntent().getStringExtra("amount");
            price = getIntent().getStringExtra("price");
            byte[] image = getIntent().getByteArrayExtra("image");
            category=getIntent().getStringExtra("category");

            // Set Intent Data
            name_input1.setText(name);
            amount_input1.setText(amount);
            price_input1.setText(price);

            // Set the image directly from byte array
            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.categories_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //set the spinner value
            spinnerCategory1.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
        }
    }

}