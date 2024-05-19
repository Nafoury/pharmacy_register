package com.example.pharamacydrugregisteration;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PofileFragment extends Fragment {
    Bitmap bitmap;
    Button logout, updateInfo;
    TextView username, email, password;
    ImageView userPhoto;
    DbSchemaSqlite db;
    ActionBar actionBar;
    FirebaseAuth auth;
    FirebaseUser user;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pofile, container, false);

        updateInfo = rootView.findViewById(R.id.updateuser);
        logout = rootView.findViewById(R.id.logout);
        username = rootView.findViewById(R.id.usernameU);
        userPhoto = rootView.findViewById(R.id.userImageU);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getActivity(), ActivityLogin.class);
            startActivity(intent);
            getActivity().finish();
        }

        db = new DbSchemaSqlite(getActivity());

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("User Profile");
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable back arrow
        }

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    if (getActivity() != null) {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result);
                        // Set the selected image to ImageView or do whatever you want with it
                        userPhoto.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        userPhoto.setOnClickListener(v -> openGallery());

        updateInfo.setOnClickListener(v -> {
            if (username.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "You can't leave the name field empty", Toast.LENGTH_SHORT).show();
            } else {
                byte[] imageData = null;
                if (bitmap != null) {
                    imageData = getBitmapAsByteArray(bitmap);
                }
                insertUserData(username.getText().toString().trim(), imageData);
            }
        });

        Cursor userData = db.getAllUsers();
        if (userData.moveToFirst()) {
            // Move to first row of the cursor
            String userName = userData.getString(userData.getColumnIndexOrThrow(DbSchemaSqlite.COLUMN1_TITLE));
            byte[] userImage = userData.getBlob(userData.getColumnIndexOrThrow(DbSchemaSqlite.COLUMN1_IMAGE));

            // Display user data
            username.setText(userName);
            if (userImage != null) {
                Bitmap userBitmap = BitmapFactory.decodeByteArray(userImage, 0, userImage.length);
                userPhoto.setImageBitmap(userBitmap);
            }
        }

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), ActivityLogin.class);
            startActivity(intent);
            getActivity().finish();
        });

        return rootView;
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private void insertUserData(String name, byte[] image) {
        DbSchemaSqlite db = new DbSchemaSqlite(getContext());
        long result = db.addUser(name, image);
        if (result == -1) {
            Toast.makeText(getContext(), "Failed to add data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
