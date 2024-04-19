package com.example.pharamacydrugregisteration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbSchemaSqlite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Medicines.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "drugs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "drug_name";
    private static final String DRUGS_AMOUNT = "drugs_amount";
    private static final String DRUGS_PRICES = "drugs_prices";
    private static final String COLUMN_IMAGE = "image";

    private static  final String CATEGORY_COLUMN="category";

    private Context context;

    public DbSchemaSqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        DRUGS_AMOUNT + " INTEGER, " +
                        DRUGS_PRICES + " REAL, " +
                        COLUMN_IMAGE + " BLOB ," +
                        CATEGORY_COLUMN + " TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addDrug(String name, int amount, float price, byte[] image,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(DRUGS_AMOUNT, amount);
        cv.put(DRUGS_PRICES, price);
        cv.put(COLUMN_IMAGE, image);
        cv.put(CATEGORY_COLUMN,category);
        return db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getDataByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_TITLE, DRUGS_AMOUNT, DRUGS_PRICES, COLUMN_IMAGE};
        String selection = CATEGORY_COLUMN + "=?";
        String[] selectionArgs = {category};
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }
    void updateData(String row_id, String name, String amount, String price, byte[] image, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(DRUGS_AMOUNT, amount);
        cv.put(DRUGS_PRICES, price);
        cv.put(COLUMN_IMAGE, image);
        cv.put(CATEGORY_COLUMN, category);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteDrug(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result > 0) {
            Toast.makeText(context, "Drug deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete drug", Toast.LENGTH_SHORT).show();
        }
    }

}
