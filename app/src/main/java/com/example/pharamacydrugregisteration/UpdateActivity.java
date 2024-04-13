package com.example.pharamacydrugregisteration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText name_input1, amount_input1, price_input1;
    Button update_button;
    ImageView imageView1;
    Spinner spinnerCategory1;
    String amount,price,name,image,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input1=findViewById(R.id.drug_name1);
        amount_input1=findViewById(R.id.drug_amount1);
        price_input1=findViewById(R.id.drug_price1);
        update_button=findViewById(R.id.update_drug);
        imageView1=findViewById(R.id.drug_image1);
        spinnerCategory1=findViewById(R.id.spinner_category);

        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbSchemaSqlite dbSchemaSqlite=new DbSchemaSqlite(UpdateActivity.this);
                //dbSchemaSqlite.updateData(name,amount,price,image,category);

            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("amount") && getIntent().hasExtra("price")&&
                getIntent().hasExtra("category") && getIntent().hasExtra("image")){
            //Getting the data from Intent
            name=getIntent().getStringExtra("name");
            amount=getIntent().getStringExtra("amount");
            price=getIntent().getStringExtra("price");
            image=getIntent().getStringExtra("image");
            category=getIntent().getStringExtra("category");

            //Setting Intent Data
            name_input1.setText(name);
            amount_input1.setText(amount);
            price_input1.setText(price);
            imageView1.setImageResource(Integer.parseInt(image));


        }else {
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
        }
    }
}