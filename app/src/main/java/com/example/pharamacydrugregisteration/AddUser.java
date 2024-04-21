package com.example.pharamacydrugregisteration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddUser extends AppCompatActivity {

    Bitmap bitmap1;
    EditText user_input,user2_input;
    Button add_user;
    ImageView imageUser;

    private ActivityResultLauncher<String> galleryLauncher1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        user_input=findViewById(R.id.username);
        user2_input=findViewById(R.id.usersurname);
        imageUser=findViewById(R.id.userimage);
        add_user=findViewById(R.id.saveuser);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add User Information");

            actionBar.setDisplayHomeAsUpEnabled(true); // Enable back arrow
        }

        galleryLauncher1 = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                    // Set the selected image to ImageView or do whatever you want with it
                    imageUser.setImageBitmap(bitmap1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        imageUser.setOnClickListener(v -> openGallery());

        add_user.setOnClickListener(v -> {
            byte[] imageData = getBitmapAsByteArray(bitmap1);
            insertUserData(user_input.getText().toString().trim(),
                    user2_input.getText().toString().trim(),

                    imageData);
        });


    }
    private void openGallery() {
        galleryLauncher1.launch("image/*");
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
    private void insertUserData(String name, String surname ,byte[] image) {
        DbSchemaSqlite db = new DbSchemaSqlite(this);
        long result = db.addUser(name, surname, image);
        if (result == -1) {
            Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}