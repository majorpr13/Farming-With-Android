package com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/19/2015.
 */
public class SQLHelper_Fields extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FieldsManager";
    private static final String TABLE_FIELD_INFO = "fieldInfo";
    private static final String TABLE_FIELD_BOUNDS = "fieldBounds";

    private static final String KEY_FIELDINFO_ID = "field_id";
    private static final String KEY_FIELDBOUNDS_ID = "bounds_id";
    private static final String KEY_FIELD_NAME = "name";
    private static final String KEY_FIELD_TYPE = "type";
    private static final String KEY_FIELD_DESCRIPTION = "description";
    private static final String KEY_FIELD_BOUNDARIES_LAT = "boundaries_lat";
    private static final String KEY_FIELD_BOUNDARIES_LON = "boundaries_lon";

    public SQLHelper_Fields(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FIELDINFO_TABLE = "CREATE TABLE " + TABLE_FIELD_INFO + "("
                + KEY_FIELDINFO_ID + " INTEGER PRIMARY KEY," + KEY_FIELD_NAME + " TEXT" + KEY_FIELD_TYPE + " TEXT" + KEY_FIELD_DESCRIPTION + " TEXT" + ")";
        String CREATE_FIELDBOUNDS_TABLE = "CREATE TABLE " + TABLE_FIELD_BOUNDS + "("
                + KEY_FIELDBOUNDS_ID + " INTEGER PRIMARY KEY," +KEY_FIELD_NAME + "TEXT" + KEY_FIELD_BOUNDARIES_LAT + " NUMERIC" + KEY_FIELD_BOUNDARIES_LON + " NUMERIC" + ")";

        db.execSQL(CREATE_FIELDINFO_TABLE);
        db.execSQL(CREATE_FIELDBOUNDS_TABLE);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIELD_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIELD_BOUNDS);
        //Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Field
    public void addField(FieldHelper field) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_NAME, field.getFieldName()); // Field Name
        values.put(KEY_FIELD_TYPE, field.getCropType());
        values.put(KEY_FIELD_DESCRIPTION, field.getFieldDescriptor());
        db.insert(TABLE_FIELD_INFO, null, values);

        List<LatLng> tmpList = field.getPolygonList();
        String tmpFieldName = field.getFieldName();
            if (tmpList.size() > 0) {
                for (int i = 0; i < tmpList.size(); i++) {
                    values = new ContentValues();
                    values.put(KEY_FIELD_NAME, tmpFieldName);
                    values.put(KEY_FIELD_BOUNDARIES_LAT, tmpList.get(i).latitude);
                    values.put(KEY_FIELD_BOUNDARIES_LON, tmpList.get(i).longitude);
                    db.insert(TABLE_FIELD_BOUNDS, null, values);

                }
            } else {
                values = new ContentValues();
                values.put(KEY_FIELD_NAME, tmpFieldName);
                values.put(KEY_FIELD_BOUNDARIES_LAT, 0.0);
                values.put(KEY_FIELD_BOUNDARIES_LON, 0.0);
                db.insert(TABLE_FIELD_BOUNDS, null, values);
            }
        // Inserting Row
        db.close(); // Closing database connection
    }

    // Getting single field
    public FieldHelper getField(String desiredFieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_FIELD_BOUNDS + " WHERE " + KEY_FIELD_NAME + " = " + desiredFieldName;
        FieldHelper field = new FieldHelper();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            field = new FieldHelper(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
                // Adding contact to list
        }
        // return contact
        return field;
    }

    // Getting All Fields
    public List<FieldHelper> getAllFields() {
        List<FieldHelper> fieldList = new ArrayList<FieldHelper>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FIELD_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FieldHelper field = new FieldHelper(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
                // Adding contact to list
                fieldList.add(field);
            } while (cursor.moveToNext());
        }

        // return contact list
        return fieldList;
    }

    public List<LatLng> getAllBounds(String desiredFieldName){
        List<LatLng> tmpList = new ArrayList<LatLng>();
        String selectQuery = "SELECT * FROM " + TABLE_FIELD_BOUNDS + " WHERE " + KEY_FIELD_NAME + " = " + desiredFieldName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LatLng tmpLatLng = new LatLng(Double.parseDouble(cursor.getString(1)) , Double.parseDouble(cursor.getString(2)));
                // Adding contact to list
                tmpList.add(tmpLatLng);
            } while (cursor.moveToNext());
        }
        return(tmpList);
    }


    // Updating single field
    public void updateField(FieldHelper field) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_NAME, field.getFieldName());
        values.put(KEY_FIELD_TYPE, field.getCropType());
        values.put(KEY_FIELD_DESCRIPTION, field.getFieldDescriptor());

        // updating row
        db.update(TABLE_FIELD_INFO, values, KEY_FIELD_NAME + " = ?",
                new String[] { String.valueOf(field.getFieldName()) });

        //Let us remove all of the previous boundaries that defined the field
        db.delete(TABLE_FIELD_BOUNDS, KEY_FIELD_NAME + " = ?", new String[]{String.valueOf(field.getFieldName())});

        //Now let us write in the new ones
        List<LatLng> tmpList = field.getPolygonList();
        String tmpFieldName = field.getFieldName();
        if (tmpList.size() > 0) {
            for (int i = 0; i < tmpList.size(); i++) {
                values = new ContentValues();
                values.put(KEY_FIELD_NAME, tmpFieldName);
                values.put(KEY_FIELD_BOUNDARIES_LAT, tmpList.get(i).latitude);
                values.put(KEY_FIELD_BOUNDARIES_LON, tmpList.get(i).longitude);
                db.insert(TABLE_FIELD_BOUNDS, null, values);

            }
        } else {
            values = new ContentValues();
            values.put(KEY_FIELD_NAME, tmpFieldName);
            values.put(KEY_FIELD_BOUNDARIES_LAT, 0.0);
            values.put(KEY_FIELD_BOUNDARIES_LON, 0.0);
            db.insert(TABLE_FIELD_BOUNDS, null, values);
        }

    }

    // Deleting single field
    public void deleteField(FieldHelper field) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FIELD_INFO, KEY_FIELD_NAME + " = ?", new String[]{String.valueOf(field.getFieldName())});
        db.delete(TABLE_FIELD_BOUNDS, KEY_FIELD_NAME + " = ?", new String[]{String.valueOf(field.getFieldName())});
        db.close();
    }


    public void clearAllFields(){

    }


    // Getting fields Count
    public int getFieldsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FIELD_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting fields Count
    public int getBoundariesCount(String desiredFieldName) {
        String countQuery = "SELECT * FROM " + TABLE_FIELD_BOUNDS + " WHERE " + KEY_FIELD_NAME + " = " + desiredFieldName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
