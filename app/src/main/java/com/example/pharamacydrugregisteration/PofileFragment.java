package com.example.pharamacydrugregisteration;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PofileFragment extends Fragment {

    Button addInfo,updateInfo;
    TextView textView;
    ImageView userPhoto;
    DbSchemaSqlite db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pofile, container, false);

        addInfo = rootView.findViewById(R.id.button);
        updateInfo=rootView.findViewById(R.id.button2);
        textView=rootView.findViewById(R.id.namesurname);
        userPhoto=rootView.findViewById(R.id.profileImage);
        db = new DbSchemaSqlite(getActivity());
        displayUserData();
        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the AddUser activity
                Intent intent = new Intent(getActivity(), AddUser.class);

                // Start the AddUser activity
                startActivity(intent);
            }
        });

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the AddUser activity
                Intent intent = new Intent(getActivity(), UpdateUser.class);

                // Start the AddUser activity
                startActivity(intent);
            }
        });
        return  rootView;
    }

    private void displayUserData() {
        Cursor cursor = db.getAllUsers();

        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String surname = cursor.getString(2);
            byte[] image = (cursor.getBlob(3));

            // Set the name and surname to the TextView
            textView.setText(name + " " + surname);

            // Set the user photo to the ImageView
            if (image != null) {
                userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
        }
    }
}