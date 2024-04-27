package com.example.pharamacydrugregisteration;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PofileFragment extends Fragment {

    Button logout,updateInfo;
    TextView username,email,password;
    ImageView userPhoto;
    DbSchemaSqlite db;
    ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pofile, container, false);

        updateInfo = rootView.findViewById(R.id.updateuser);
        logout=rootView.findViewById(R.id.logout);
        username=rootView.findViewById(R.id.usernameU);
        email=rootView.findViewById(R.id.email);
        password=rootView.findViewById(R.id.password);
        userPhoto=rootView.findViewById(R.id.userImageU);
        db = new DbSchemaSqlite(getActivity());

          actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle( "User Profile");
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable back arrow
        }

        return  rootView;
    }

  }