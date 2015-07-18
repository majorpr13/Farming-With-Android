package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kkroegeraraustech.farmingwithandroid.helpers.SoilHelper;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class Soil_Item extends Area_of_Interest {

    public static final String TABLE_NAME = "Soil_Items";
    public static final String COL_ID = AbstractModel.COL_ID;
    public static final String COL_AOI_ID = "Area_of_Interest_ID";
    public static final String COL_NAME = "Soil Sample Name";
    public static final String COL_LAT = "Sample Latitude";
    public static final String COL_LON = "Sample Longitude";

    public static String getSql() {
        return UtilItem_Support.concat("CREATE TABLE ", TABLE_NAME, " (",
                COL_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ",
                COL_AOI_ID, " INTEGER, ",
                COL_NAME, " TEXT, ",
                COL_LAT, " REAL",
                COL_LON, " REAL",
                ");");
    }

    long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_AOI_ID, noteId);
        cv.put(COL_NAME, name==null ? "" : name);
        //cv.put(COL_LAT, mSoilHelper.getValue_Latitude());
        //cv.put(COL_LON, mSoilHelper.getValue_Longitude());

        return db.insert(TABLE_NAME, null, cv);
    }

    boolean update(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        if (noteId > 0)
            cv.put(COL_AOI_ID, noteId);
        if (name != null)
            cv.put(COL_NAME, name);
        if (mSoilHelper != null) {
            //cv.put(COL_LAT, mSoilHelper.getValue_Latitude());
            //cv.put(COL_LON, mSoilHelper.getValue_Longitude());
        }

        return db.update(TABLE_NAME, cv, COL_ID+" = ?", new String[]{String.valueOf(id)})
                == 1 ? true : false;
    }

    public boolean load(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                reset();
                id = cursor.getLong(cursor.getColumnIndex(COL_ID));
                noteId = cursor.getLong(cursor.getColumnIndex(COL_AOI_ID));
                name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                //mSoilHelper.setValueGPS(cursor.getDouble(cursor.getColumnIndex(COL_LAT)), cursor.getDouble(cursor.getColumnIndex(COL_LON)));
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    public static Cursor list(SQLiteDatabase db, String noteId) {
        if (noteId != null)
            return db.query(TABLE_NAME, null, COL_AOI_ID+" = ?", new String[]{noteId}, null, null, COL_ID+" ASC");
        else
            return null;
    }

    public boolean delete(SQLiteDatabase db) {
        return db.delete(TABLE_NAME, COL_ID+" = ?", new String[]{String.valueOf(id)})
                == 1 ? true : false;
    }

    //--------------------------------------------------------------------------

    private long noteId;
    private String name;
    private SoilHelper mSoilHelper;

    public void reset() {
        id = 0;
        noteId = 0;
        name = null;
        mSoilHelper = null;
    }

    public long getNoteId() {
        return noteId;
    }
    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public SoilHelper getSoilSample() {
        return mSoilHelper;
    }
    public void setSoilSample(SoilHelper soilHelper) {
        this.mSoilHelper = soilHelper;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        return id == ((Attachment)obj).id;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
