package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.GridLayout;

import com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper.SQProtection;
import com.kkroegeraraustech.farmingwithandroid.helpers.AreaHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;

import java.util.List;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class SpecificField extends BaseModel {
    public static final String TABLE_NAME = "Specific Field";
    public static final String COL_ID = BaseModel.COL_ID;
    public static final String COL_CREATEDTIME = BaseModel.COL_CREATEDTIME;
    public static final String COL_MODIFIEDTIME = BaseModel.COL_MODIFIEDTIME;
    public static final String COL_NAME = "Field Name";

    public static String getSql() {
        return UtilItem_Support.concat("CREATE TABLE ", TABLE_NAME, " (",
                BaseModel.getSql(),
                COL_NAME, " TEXT",
                ");");
    }

    long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.save(cv);
        cv.put(COL_NAME, name==null ? "" : name);

        return db.insert(TABLE_NAME, null, cv);
    }

    boolean update(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.update(cv);
        if (name != null)
            cv.put(COL_NAME, name);

        return db.update(TABLE_NAME, cv, COL_ID+" = ?", new String[]{String.valueOf(id)})
                == 1 ? true : false;
    }

    public boolean load(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                reset();
                super.load(cursor);
                name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    public static Cursor list(SQLiteDatabase db) {
        String[] columns = {COL_ID, COL_NAME};
        // TODO: Check the commented below...should only handle the locked columns
        String selection = !SQProtection.showLocked() ? COL_LOCKED+" <> 1" : null;
        return db.query(TABLE_NAME, columns, selection, null, null, null, COL_CREATEDTIME+" ASC");
    }

    public boolean delete(SQLiteDatabase db) {
        boolean status = false;
        String[] whereArgs = new String[]{String.valueOf(id)};
        String whereClause = UtilItem_Support.concat(Attachment.COL_NOTEID, " IN (SELECT ", Note_Item.COL_ID, " FROM ", Note_Item.TABLE_NAME, " WHERE ", Note_Item.COL_CATEGORYID, " = ?)");

        db.beginTransaction();
        try {
            db.delete(Area_of_Interest.TABLE_NAME, whereClause, whereArgs);
            status = db.delete(TABLE_NAME, COL_ID+" = ?", whereArgs)
                    == 1 ? true : false;
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return status;
    }

    //--------------------------------------------------------------------------

    private String name;
    private List<Area_of_Interest> mAOIS;
    private GPSHelper mGPSHelper;
    private FieldHelper mFieldHelper;
    private AreaHelper mFieldArea;

    public void reset() {
        super.reset();
        name = null;
        mAOIS = null;
        mFieldHelper = null;
        mFieldArea = null;
    }

    public SpecificField() {}

    public SpecificField(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public List<Area_of_Interest> getAOIS() {
        return mAOIS;
    }
    public void setAOIS(List<Area_of_Interest> notes) {
        this.mAOIS = notes;
    }


    public GPSHelper getGPSHelper() {
        return mGPSHelper;
    }
    public void setGPSHelper(GPSHelper positionGPS) {
        this.mGPSHelper = positionGPS;
    }


    public FieldHelper getFieldHelper() {
        return mFieldHelper;
    }
    public void setFieldHelper(FieldHelper fieldHelper) {
        this.mFieldHelper = fieldHelper;
    }


    public AreaHelper getAreaHelper() {
        return mFieldArea;
    }
    public void setAreaHelper(AreaHelper areaHelper) {
        this.mFieldArea = areaHelper;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        return id == ((SpecificField)obj).id;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
