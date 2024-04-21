package com.example.pharamacydrugregisteration;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PofileFragment extends Fragment {

    Button addInfo,updateInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pofile, container, false);

        addInfo = rootView.findViewById(R.id.button); // Initialize the button

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the AddUser activity
                Intent intent = new Intent(getActivity(), AddUser.class);

                // Start the AddUser activity
                startActivity(intent);
            }
        });
        return  rootView;
    }
}