package com.example.pharamacydrugregisteration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.BitmapKt;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder> {

private Context context;
private ArrayList drug_name,drug_image,drug_amount,drug_price;
    DrugAdapter( Context context,

                 ArrayList drug_name,
                 ArrayList drug_image,
                 ArrayList drug_amount,
                 ArrayList drug_price){
        this.context=context;

        this.drug_name=drug_name;
        this.drug_image=drug_image;
        this.drug_amount=drug_amount;
        this.drug_price=drug_price;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        byte[] imageBytes = (byte[]) drug_image.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Set the bitmap to ImageView
        holder.imageView.setImageBitmap(bitmap);
        holder.drug_name.setText(String.valueOf(drug_name.get(position)));
        holder.drug_amount.setText(String.valueOf(drug_amount.get(position)) + " pieces");

        // Set drug price with dollar sign prefix
        holder.drug_price.setText("₺" + String.valueOf(drug_price.get(position)));

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to show the popup menu with edit and delete options
                PopupMenu popupMenu = new PopupMenu(context, holder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
               
                popupMenu.show();
            }
        });

    }
    private void onEditClicked() {
        Toast.makeText(context, "Edit option clicked", Toast.LENGTH_SHORT).show();
    }

    private void onDeleteClicked() {
        Toast.makeText(context, "Delete option clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return drug_name.size(); // Return the size of the data list
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton imageButton;
        TextView drug_name,drug_amount,drug_price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.drug_photo);
            drug_name=itemView.findViewById(R.id.drug_name);
            drug_amount=itemView.findViewById(R.id.drug_amount);
            drug_price=itemView.findViewById(R.id.drug_price);
            imageButton=itemView.findViewById(R.id.more_button);

        }
    }
}
