package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport;

import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kkroegeraraustech.farmingwithandroid.adapters.SQLHelper.SQProtection;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 * This class of Note Item extends the Area of Interest Class
 * This class handles the specific note writing into the SQL Database
 */
public class Note_Item extends Area_of_Interest{
    public static final String TABLE_NAME = "Note";
    public static final String COL_ID = BaseModel.COL_ID;
    public static final String COL_CREATEDTIME = BaseModel.COL_CREATEDTIME;
    public static final String COL_MODIFIEDTIME = BaseModel.COL_MODIFIEDTIME;
    public static final String COL_CATEGORYID = " Category_id";
    public static final String COL_TITLE = "Title";
    public static final String COL_CONTENT = "Content";


    public static String getSql() {
        return UtilItem_Support.concat("CREATE TABLE ", TABLE_NAME, " (",
                BaseModel.getSql(),
                COL_CATEGORYID, " INTEGER, ",
                COL_TITLE, " TEXT, ",
                COL_CONTENT, " TEXT, ",
                 ");");
    }

    long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.save(cv);
        cv.put(COL_CATEGORYID, categoryId);
        cv.put(COL_TITLE, title==null ? "" : title);
        cv.put(COL_CONTENT, content==null ? "" : content);
        return db.insert(TABLE_NAME, null, cv);
    }

    boolean update(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.update(cv);
        if (categoryId > 0)
            cv.put(COL_CATEGORYID, categoryId);
        if (title != null)
            cv.put(COL_TITLE, title);
        if (content != null)
            cv.put(COL_CONTENT, content);

        return db.update(TABLE_NAME, cv, COL_ID+" = ?", new String[]{String.valueOf(id)})
                == 1 ? true : false;
    }

    public boolean load(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                reset();
                super.load(cursor);
                categoryId = cursor.getLong(cursor.getColumnIndex(COL_CATEGORYID));
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                content = cursor.getString(cursor.getColumnIndex(COL_CONTENT));
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    /**
     * @param db This is the SQLDatabase that is providing the storage
     * @param args {categoryId, orderBy}
     * @return cursor
     */
    public static Cursor list(SQLiteDatabase db, String... args) {
        String categoryId = args!=null ? args[0] : null;

        String[] columns = {COL_ID, COL_TITLE, COL_MODIFIEDTIME, COL_CREATEDTIME};
        String selection = "1 = 1";
        // TODO: Check the commented below...should only handle the locked columns
        selection += !SQProtection.showLocked() ? " AND "+COL_LOCKED+" <> 1" : "";
        selection += categoryId!=null ? " AND "+COL_CATEGORYID+" = "+categoryId : "";
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
    private String title;
    private String content;
    private List<Attachment> attachments;

    public void reset() {
        super.reset();
        categoryId = 0;
        title = null;
        content = null;
        attachments = null;
    }

    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Note_Item() {}

    public Note_Item(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        return id == ((Note_Item)obj).id;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
