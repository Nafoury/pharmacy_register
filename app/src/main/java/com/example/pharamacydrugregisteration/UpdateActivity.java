package com.example.pharamacydrugregisteration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    EditText name_input1, amount_input1, price_input1,produce,end;
    Bitmap bitmap;
    Button update_button;
    ImageView imageView1;
    Spinner spinnerCategory1;
    String amount,price,name,category,id,image,start1,end1;
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
        produce=findViewById(R.id.produceDate);
        end=findViewById(R.id.expireDate);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Update drug");

            actionBar.setDisplayHomeAsUpEnabled(true); // Enable back arrow
        }

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

        produce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog theDialog=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        produce.setText(year+"/"+month+"/"+day);

                    }
                },year,month,day);

                theDialog.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog theDialog=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        end.setText(year+"/"+month+"/"+day);
                    }
                },year,month,day);

                theDialog.show();
            }
        });
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from UI components
                name = name_input1.getText().toString().trim();
                amount = amount_input1.getText().toString().trim();
                price = price_input1.getText().toString().trim();
                category = spinnerCategory1.getSelectedItem().toString().trim();
               start1= produce.getText().toString().trim();
               end1=end.getText().toString().trim();


                // Convert bitmap to byte array
                byte[] newImage = getBitmapAsByteArray(bitmap);



                // Update data in database
                DbSchemaSqlite dbSchemaSqlite = new DbSchemaSqlite(UpdateActivity.this);
                dbSchemaSqlite.updateData(id,name,amount,price,newImage, category,start1,end1);
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
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("amount") && getIntent().hasExtra("price") &&
                getIntent().hasExtra("category") && getIntent().hasExtra("start") && getIntent().hasExtra("end") && getIntent().hasExtra("image")){
            // Getting the data from Intent
            id=getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            amount = getIntent().getStringExtra("amount");
            price = getIntent().getStringExtra("price");
            byte[] image = getIntent().getByteArrayExtra("image");
            category=getIntent().getStringExtra("category");
            start1 = getIntent().getStringExtra("start");
            end1 = getIntent().getStringExtra("end");
            // Set Intent Data
            name_input1.setText(name);
            amount_input1.setText(amount);
            price_input1.setText(price);
            produce.setText(start1);
            end.setText(end1);

            // Set the image directly from byte array
            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));

            String[] categoriesArray = getResources().getStringArray(R.array.categories_array);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, categoriesArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory1.setAdapter(adapter);

            // Logging the data before update
            Log.d("UpdateActivity", "Name: " + name);
            Log.d("UpdateActivity", "Amount: " + amount);
            Log.d("UpdateActivity", "Price: " + price);
            Log.d("UpdateActivity", "Category: " + category);


            // Set the spinner value to the category obtained from intent
            int categoryIndex = Arrays.asList(categoriesArray).indexOf(category);
            if (categoryIndex != -1) {
                spinnerCategory1.setSelection(categoryIndex);
            } else {
                // Category not found in the spinner items
                // You might want to handle this case based on your app's logic
                // For example, you could set a default category or display an error message
            }
        } else {
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
        }
    }



}