package com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.SoilHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.SoilHelper_SQL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Ken Heron Systems on 7/24/2015.
 */
public class SQLHelper_Soils extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SoilsManager";
    private static final String TABLE_SAMPLE_INFO = "SampleInfo";
    private static final String TABLE_SAMPLES = "SoilSamples";

    private static final String KEY_GENERAL_INFO_ID = "info_id";
    private static final String KEY_SAMPLES_ID = "samples_id";

    private static final String KEY_FIELD_NAME = "name";
    private static final String KEY_AREA_NAME= "area";
    private static final String KEY_HIGH_PH = "high_ph";
    private static final String KEY_LOW_PH = "low_ph";

    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";

    private static final String KEY_SAMPLE_LAT = "boundaries_lat";
    private static final String KEY_SAMPLE_LON = "boundaries_lon";
    private static final String KEY_SAMPLE_COMPACTION = "compaction";
    private static final String KEY_SAMPLE_PH = "measured_ph";

    public SQLHelper_Soils(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SAMPLEINFO_TABLE = "CREATE TABLE " + TABLE_SAMPLE_INFO + "("
                + KEY_GENERAL_INFO_ID + " INTEGER PRIMARY KEY," + KEY_FIELD_NAME + " TEXT " + KEY_AREA_NAME + " TEXT "
                + KEY_YEAR + "INT " + KEY_MONTH + " INT " + KEY_DAY + " INT "
                + KEY_HIGH_PH + " NUM" + KEY_LOW_PH + " NUM" + ")";

        String CREATE_SAMPLES_TABLE = "CREATE TABLE " + TABLE_SAMPLES + "("
                + KEY_SAMPLES_ID + " INTEGER PRIMARY KEY," + KEY_FIELD_NAME + " TEXT" + KEY_AREA_NAME + " TEXT"
                + KEY_YEAR + "INT" + KEY_MONTH + " INT" + KEY_DAY + " INT"
                + KEY_SAMPLE_LAT + "NUMERIC" + KEY_SAMPLE_LON + " NUMERIC"
                + KEY_SAMPLE_COMPACTION + "NUMERIC" + KEY_SAMPLE_PH + " NUMERIC" + ")";

        db.execSQL(CREATE_SAMPLEINFO_TABLE);
        db.execSQL(CREATE_SAMPLES_TABLE);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLE_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLES);
        //Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Sample
    public void addSample(SoilHelper soilSample) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_NAME, soilSample.getFieldName()); // Field Name
        values.put(KEY_AREA_NAME, soilSample.getAreaName()); // Area Name
        values.put(KEY_HIGH_PH, soilSample.getExpectedPH_HIGH()); // HIGH pH
        values.put(KEY_LOW_PH, soilSample.getExpectedPH_LOW()); // Low pH

        db.insert(TABLE_SAMPLE_INFO, null, values);


        List<SoilHelper.SoilDetails> tmpList = soilSample.getAllSamples();
        String tmpFieldName = soilSample.getFieldName();
        String tmpAreaName = soilSample.getAreaName();

        int tempYear = soilSample.getDate().get(Calendar.YEAR);
        int tempMonth = soilSample.getDate().get(Calendar.MONTH);
        int tempDay = soilSample.getDate().get(Calendar.DAY_OF_MONTH);

        if (tmpList.size() > 0) {
            for (int i = 0; i < tmpList.size(); i++) {
                values = new ContentValues();
                values.put(KEY_FIELD_NAME, tmpFieldName);
                values.put(KEY_AREA_NAME, tmpAreaName);
                values.put(KEY_YEAR, tempYear);
                values.put(KEY_MONTH, tempMonth);
                values.put(KEY_DAY, tempDay);
                values.put(KEY_SAMPLE_LAT, tmpAreaName);
                values.put(KEY_SAMPLE_LON, tmpFieldName);
                values.put(KEY_SAMPLE_COMPACTION, tmpAreaName);
                values.put(KEY_SAMPLE_PH, tmpAreaName);

                db.insert(TABLE_SAMPLES, null, values);

            }
        }
        // Inserting Row
        db.close(); // Closing database connection
    }

    //Get the unique fields

    /**
     *
     * @return
     */
    public List<String> getAvailableFeilds_SoilInfo(){
        List<String> tmpList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT " +  KEY_FIELD_NAME + " FROM " + TABLE_SAMPLE_INFO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            tmpList.add(cursor.getString(0));
            // Adding field name to list
        }
        // return list
        return tmpList;
    }

    //Get the unique areas

    /**
     *
     * @param desiredField
     * @return
     */
    public List<String> getAvailableAreas_SoilInfo(String desiredField){
        List<String> tmpList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT " +  KEY_AREA_NAME + " FROM " + TABLE_SAMPLE_INFO
                + " WHERE " + KEY_FIELD_NAME + " = " + desiredField;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            tmpList.add(cursor.getString(0));
            // Adding field name to list
        }
        // return list
        return tmpList;
    }

    //Get the unique dates

    /**
     *
     * @param desiredField
     * @param desArea
     * @return
     */
    public List<GregorianCalendar> getAvailableDates_SoilInfo(String desiredField , String desArea){
        List<GregorianCalendar> tmpList = new ArrayList<GregorianCalendar>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " +  KEY_YEAR + "," + KEY_MONTH + "," + KEY_DAY
                + " WHERE " + KEY_FIELD_NAME + " = " + desiredField
                + " WHERE " + KEY_AREA_NAME + " = " + desArea;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            GregorianCalendar tmpDate = new GregorianCalendar();
            tmpDate.set(Integer.parseInt(cursor.getString(0)) , Integer.parseInt(cursor.getString(1)) , Integer.parseInt(cursor.getString(2)));
            tmpList.add(tmpDate);
            // Adding field name to list
        }
        // return list
        return tmpList;
    }

    /**
     *
     * @param desiredField
     * @param desArea
     * @param desYear
     * @param desMonth
     * @param desDay
     * @return
     */
    public List<SoilHelper_SQL> getSoilSamples(String desiredField, String desArea, int desYear, int desMonth, int desDay)
    {
        List<SoilHelper_SQL> tmpList = new ArrayList<SoilHelper_SQL>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " +  KEY_SAMPLE_LAT + "," + KEY_SAMPLE_LON + "," + KEY_SAMPLE_COMPACTION + "," + KEY_SAMPLE_PH
                + " WHERE " + KEY_FIELD_NAME + " = " + desiredField
                + " WHERE " + KEY_AREA_NAME + " = " + desArea
                + " WHERE " + KEY_YEAR + " = " + desYear
                + " WHERE " + KEY_MONTH + " = " + desMonth
                + " WHERE " + KEY_DAY + " = " + desDay;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            SoilHelper_SQL tmpSample = new SoilHelper_SQL();
            tmpSample.setValues(Double.parseDouble(cursor.getString(0)), Double.parseDouble(cursor.getString(1)),
                    Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4)));
            tmpList.add(tmpSample);
            // Adding field name to list
        }
        // return list
        return tmpList;
    }

    //Get the appropriate field and area information
    public SoilHelper getField_Area_SoilInfo(String desiredFieldName,String desiredAreaName) {
        //set up the database and get all readable
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SAMPLE_INFO + " WHERE " + KEY_FIELD_NAME + " = " + desiredFieldName + " WHERE " + KEY_AREA_NAME + " = " + desiredAreaName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        SoilHelper soilTemp = null;
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            soilTemp = new SoilHelper(cursor.getString(0),cursor.getString(1));
            soilTemp.setCollectionDate(Integer.parseInt(cursor.getString(2)) , Integer.parseInt(cursor.getString(3)) , Integer.parseInt(cursor.getString(4)));
            soilTemp.setPHLevels(Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));
            soilTemp.setCompactionLevels(Double.parseDouble(cursor.getString(6)) , Double.parseDouble(cursor.getString(7)));
            // Adding contact to list
        }
        // return contact
        return soilTemp;
    }

}
