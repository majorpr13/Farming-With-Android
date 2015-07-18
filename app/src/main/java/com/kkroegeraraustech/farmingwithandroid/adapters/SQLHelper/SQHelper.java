package com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.Area_of_Interest;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.Attachment;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.Field_Items;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.Note_Item;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.Soil_Item;
import com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport.SpecificField;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class SQHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "FarmingWithAndroid.db";
    public static final int DB_VERSION = 1;

    public SQHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SpecificField.getSql());
        db.execSQL(Area_of_Interest.getSql());
        db.execSQL(Note_Item.getSql());
        db.execSQL(Attachment.getSql());
        db.execSQL(Soil_Item.getSql());
        db.execSQL(Field_Items.getSql());

        populateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SpecificField.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Area_of_Interest.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Note_Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Attachment.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Soil_Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Field_Items.TABLE_NAME);
        onCreate(db);
    }

    private void populateData(SQLiteDatabase db) {
    /*
        // Public
        SpecificField category = new SpecificField();
        category.setName("Public");
        long categoryId = category.save(db);
        SmartPad.PUBLIC_CATEGORYID = categoryId;

        Note_Item note = new Note_Item();
        note.setCategoryId(categoryId);
        note.setTitle("Read me");
        note.setType(Note.BASIC);
        note.setContent("This app allows you to create notes, checklists, and snapshots quickly and easily. \n\nAdditionally, you may create folders to organize your notes and password protect them as well.\n\nRemember to long press an item to see more options.");
        note.save(db);

        // Personal
        category.reset();
        category.setName("Personal");
        category.setLocked(true);
        categoryId = category.save(db);

        note.reset();
        note.setCategoryId(categoryId);
        note.setTitle("To do");
        note.setType(Note.CHECKLIST);
        long noteId = note.save(db);
        CheckItem ci = new CheckItem();
        ci.setNoteId(noteId);
        ci.setName("Set password in Settings page");
        ci.save(db);
        ci.reset();
        ci.setNoteId(noteId);
        ci.setName("Rate this app on Google Play");
        ci.save(db);
        ci.reset();
        ci.setNoteId(noteId);
        ci.setName("Visit www.appsrox.com");
        ci.save(db);
    */
    }
}
