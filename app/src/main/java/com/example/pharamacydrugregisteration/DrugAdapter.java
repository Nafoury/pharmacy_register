package com.example.pharamacydrugregisteration;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.PopupMenu;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.core.graphics.BitmapKt;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder> {

    private Context context;
    String id;
    private String category;
    Activity activity;
    private ArrayList drug_name,drug_image,drug_amount,drug_price,drug_id,drug_end,drug_start;
    DrugAdapter(

            Context context,
            ArrayList drug_id,
            ArrayList drug_name,
            ArrayList drug_image,
            ArrayList drug_amount,
            ArrayList drug_price,
            String category,
            ArrayList drug_end,
            ArrayList drug_start,
            Activity aactivity

    ){
        this.context=context;

        this.drug_id=drug_id;
        this.drug_name=drug_name;
        this.drug_image=drug_image;
        this.drug_amount=drug_amount;
        this.drug_price=drug_price;
        this.category = category;
        this.drug_end=drug_end;
        this.drug_start=drug_start;
        this.activity=aactivity;


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
            public void onClick(View v) {
                // Implement the logic to show the popup menu with edit and delete options
                PopupMenu popupMenu = new PopupMenu(context, holder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_edit) {
                            // Start UpdateActivity
                            Intent intent = new Intent(context, UpdateActivity.class);
                            // Pass data to UpdateActivity if needed
                            intent.putExtra("id",String.valueOf(drug_id.get(position)));
                            intent.putExtra("name", String.valueOf(drug_name.get(position)));
                            intent.putExtra("price", String.valueOf(drug_price.get(position)));
                            intent.putExtra("amount", String.valueOf(drug_amount.get(position)));
                            intent.putExtra("image", (byte[]) drug_image.get(position));
                            intent.putExtra("category", category);
                            intent.putExtra("start",String.valueOf(drug_start.get(position)));
                            intent.putExtra("end",String.valueOf(drug_end.get(position)));

                            activity.startActivityForResult(intent,1);
                            return true;
                        } else if (item.getItemId() == R.id.menu_delete) {
                            confirmDialog();

                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (activity instanceof CategoryDisplayData) {
                    DbSchemaSqlite dbSchemaSqlite = new DbSchemaSqlite(context);

                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just close the dialog
                dialog.dismiss();
            }
        });
        builder.create().show();
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