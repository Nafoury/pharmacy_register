package com.example.pharamacydrugregisteration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFregament extends Fragment {

    FloatingActionButton addB;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_fregament, container, false);


    cardView1 = rootView.findViewById(R.id.vitamins);
        cardView2 = rootView.findViewById(R.id.hermon);
        cardView3 = rootView.findViewById(R.id.supp);
        cardView4 = rootView.findViewById(R.id.skin);
        cardView5 = rootView.findViewById(R.id.skin2);
        cardView6 = rootView.findViewById(R.id.vitamins1);

        addB = rootView.findViewById(R.id.add_button);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Intent to move to the OnBoarding activity
                Intent intent = new Intent(getActivity(), AddPage.class);

                // Start the OnBoarding activity
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Vitamins");
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Hormones");
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Skin Care");
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Antipyretic");
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Analgesics");
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDetail("Anxiety");
            }
        });

        return rootView;
    }

    private void openCategoryDetail(String category) {
        // Open CategoryDetailActivity and pass the selected category name
        Intent intent = new Intent(getActivity(), CategoryDisplayData.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}