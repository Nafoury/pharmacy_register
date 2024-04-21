package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateUser extends AppCompatActivity {

    EditText userName,userSurname;
    Button updateUser;
    ImageView imageUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        userName=findViewById(R.id.usernameU);
        userSurname=findViewById(R.id.usersurnameU);
        imageUpdate=findViewById(R.id.userImageU);
        updateUser=findViewById(R.id.updateuser);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Update User Information");

            actionBar.setDisplayHomeAsUpEnabled(true); 
        }
    }
}