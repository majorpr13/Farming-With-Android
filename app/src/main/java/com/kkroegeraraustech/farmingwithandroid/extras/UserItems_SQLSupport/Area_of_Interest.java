package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper.SQProtection;
import com.kkroegeraraustech.farmingwithandroid.helpers.AreaHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.FieldHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.GPSHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.PlantProblemsHelper;
import com.kkroegeraraustech.farmingwithandroid.helpers.SoilHelper;

import java.util.List;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class Area_of_Interest extends BaseModel {

    public static final String TABLE_NAME = "AREA OF INTEREST";
    public static final String COL_ID = BaseModel.COL_ID;
    public static final String COL_CREATEDTIME = BaseModel.COL_CREATEDTIME;
    public static final String COL_MODIFIEDTIME = BaseModel.COL_MODIFIEDTIME;
    public static final String COL_LOCKED = BaseModel.COL_LOCKED;
    public static final String COL_FIELD = "FIELD_ID";
    public static final String COL_TITLE = "AREA NAME";
    public static final String COL_LAT = "LATITUDE";
    public static final String COL_LON = "LONGITUDE";
    public static final String COL_AREA = "AREA";
    public static final String COL_CROP = "CROP";
    public static final String COL_HEALTH = "HEALTH";

    public static final String BASIC = "basic";

    public static String getSql() {
        return UtilItem_Support.concat("CREATE TABLE ", TABLE_NAME, " (",
                BaseModel.getSql(),
                COL_FIELD, " INTEGER, ",
                COL_TITLE, " TEXT, ",
                COL_LAT, " REAL, ",
                COL_LON, " REAL",
                COL_AREA, " REAL, ",
                COL_CROP, " TEXT",
                COL_HEALTH, " TEXT, ",
                ");");
    }

    long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.save(cv);
        cv.put(COL_FIELD, categoryId);
        cv.put(COL_TITLE, mName==null ? "" : mName);
        cv.put(COL_LAT, mGPSHelper.getValue_Latitude());
        cv.put(COL_LON, mGPSHelper.getValue_Longitude());
        cv.put(COL_AREA, mAreaHelper.getFieldArea(AreaHelper.Interface_Field_Unit.AREA_M));
        cv.put(COL_CROP, mFieldHelper.getCropType());
        cv.put(COL_HEALTH, mFieldHelper.getFieldDescriptor());

        return db.insert(TABLE_NAME, null, cv);
    }

    boolean update(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.update(cv);
        if (categoryId > 0)
            cv.put(COL_FIELD, categoryId);
        if (mName != null)
            cv.put(COL_TITLE, mName);
        if (mGPSHelper != null) {
            cv.put(COL_LAT, mGPSHelper.getValue_Latitude());
            cv.put(COL_LON, mGPSHelper.getValue_Longitude());
        }
        if (mAreaHelper != null)
            cv.put(COL_AREA, mAreaHelper.getFieldArea(AreaHelper.Interface_Field_Unit.AREA_M));
        if (mFieldHelper != null) {
            cv.put(COL_CROP, mFieldHelper.getCropType());
            cv.put(COL_HEALTH, mFieldHelper.getFieldDescriptor());
        }
        return db.update(TABLE_NAME, cv, COL_ID+" = ?", new String[]{String.valueOf(id)})
                == 1 ? true : false;
    }

    public boolean load(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                reset();
                super.load(cursor);
                categoryId = cursor.getLong(cursor.getColumnIndex(COL_FIELD));
                mName = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                mGPSHelper.setValueGPS(cursor.getDouble(cursor.getColumnIndex(COL_LAT)),cursor.getDouble(cursor.getColumnIndex(COL_LON)));
                mAreaHelper.setFieldArea(AreaHelper.Interface_Field_Unit.AREA_M, cursor.getDouble(cursor.getColumnIndex(COL_AREA)));
                mFieldHelper.setCropType(cursor.getString(cursor.getColumnIndex(COL_CROP)));
                mFieldHelper.setCropType(cursor.getString(cursor.getColumnIndex(COL_HEALTH)));
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    /**
     * @param db
     * @param args {categoryId, orderBy}
     * @return cursor
     */
    public static Cursor list(SQLiteDatabase db, String... args) {
        String categoryId = args!=null ? args[0] : null;

        String[] columns = {COL_ID, COL_LOCKED, COL_TITLE, COL_MODIFIEDTIME, COL_CREATEDTIME};
        String selection = "1 = 1";
        // TODO: Check the commented below...should only handle the locked columns
        selection += !SQProtection.showLocked() ? " AND "+COL_LOCKED+" <> 1" : "";
        String orderBy = (args!=null && args.length>1) ? args[1] :
                categoryId!=null ? COL_CREATEDTIME+" ASC" : COL_MODIFIEDTIME+" DESC";

        return db.query(TABLE_NAME, columns, selection, null, null, null, orderBy);
    }

    public boolean delete(SQLiteDatabase db) {
        boolean status = false;
        String[] whereArgs = new String[]{String.valueOf(id)};

        db.beginTransaction();
        try {
            db.delete(Attachment.TABLE_NAME, Attachment.COL_NOTEID+" = ?", whereArgs);
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

    private long categoryId;
    private String mName;
    private List<Note_Item> mNotes;
    private List<Attachment> mAttachment;
    private List<SoilHelper> mSoilHelper;
    private List<PlantProblemsHelper> mProblemsHelper;
    private FieldHelper mFieldHelper;
    private GPSHelper mGPSHelper;
    private AreaHelper mAreaHelper;


    public void reset() {
        super.reset();
        mName = null;

        mNotes = null;
        mAttachment = null;
        mSoilHelper = null;
        mProblemsHelper = null;
        mFieldHelper = null;
        mGPSHelper = null;
        mAreaHelper = null;
    }

    public Area_of_Interest() {}

    public Area_of_Interest(long id) {
        this.id = id;
    }


    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public List<Note_Item> getNotes() {
        return mNotes;
    }
    public void setNotes(List<Note_Item> attachments) {
        this.mNotes = attachments;
    }

    public List<Attachment> getAttachments() {
        return mAttachment;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.mAttachment = attachments;
    }

    public List<SoilHelper> getSoilHelpers() {
        return mSoilHelper;
    }
    public void setSoilHelpers(List<SoilHelper> soilHelpers) {
        this.mSoilHelper = soilHelpers;
    }

    public List<PlantProblemsHelper> getProblemHelpers() {
        return mProblemsHelper;
    }
    public void setProblemHelpers(List<PlantProblemsHelper> problemHelpers) {
        this.mProblemsHelper = problemHelpers;
    }

    public FieldHelper getFieldHelper() {
        return mFieldHelper;
    }
    public void setFieldHelper(FieldHelper fieldHelper) {
        this.mFieldHelper = fieldHelper;
    }

    public GPSHelper getGPSHelper() {
        return mGPSHelper;
    }
    public void setGPSHelper(GPSHelper positionGPS) {
        this.mGPSHelper = positionGPS;
    }

    public AreaHelper getAreaHelper() {
        return mAreaHelper;
    }
    public void setAreaHelper(AreaHelper areaHelper) {
        this.mAreaHelper = areaHelper;
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
