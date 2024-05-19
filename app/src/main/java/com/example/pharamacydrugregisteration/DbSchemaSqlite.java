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

    private static final String PRODUCTION_DATE = "start";
    private static  final String EXPIRATION_DATE="end";
    private static final String TABLE_NAME1 = "users";
    private static final String COLUMN1_ID = "_id";
    public static final String COLUMN1_TITLE = "user_name";
    public static final String COLUMN1_IMAGE = "user_image";

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
                        CATEGORY_COLUMN + " TEXT, " +
                        PRODUCTION_DATE + " TEXT, " +
                        EXPIRATION_DATE + " TEXT, " +
                        COLUMN_IMAGE + " BLOB)";
        db.execSQL(query);


        // Your existing user table creation query
        String query1 =
                "CREATE TABLE " + TABLE_NAME1 + " (" +
                        COLUMN1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN1_TITLE + " TEXT, " +
                        COLUMN1_IMAGE + " BLOB)";
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public long addDrug(String name, int amount, float price, byte[] image, String category, String productionDate, String expirationDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(DRUGS_AMOUNT, amount);
        cv.put(DRUGS_PRICES, price);
        cv.put(COLUMN_IMAGE, image);
        cv.put(CATEGORY_COLUMN, category);
        cv.put(PRODUCTION_DATE, productionDate);    // Add production date
        cv.put(EXPIRATION_DATE, expirationDate);    // Add expiration date
        return db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getDataByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_TITLE, DRUGS_AMOUNT, DRUGS_PRICES,CATEGORY_COLUMN,PRODUCTION_DATE,EXPIRATION_DATE,COLUMN_IMAGE,};
        String selection = CATEGORY_COLUMN + "=?";
        String[] selectionArgs = {category};
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }
    void updateData(String row_id, String name, String amount, String price, byte[] image, String category, String datend,String datestart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(DRUGS_AMOUNT, amount);
        cv.put(DRUGS_PRICES, price);
        cv.put(COLUMN_IMAGE, image);
        cv.put(CATEGORY_COLUMN, category);
        cv.put(PRODUCTION_DATE,datend);
        cv.put(EXPIRATION_DATE,datend);

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
    public long addUser(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN1_TITLE, name);
        cv.put(COLUMN1_IMAGE, image);
        return db.insert(TABLE_NAME1, null, cv);
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN1_ID, COLUMN1_TITLE, COLUMN1_IMAGE};
        return db.query(TABLE_NAME1, projection, null, null, null, null, null);
    }

}
