package com.pkemo.NIVBible;

/**
 * Created by Nelson on 1/14/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NIVMySQLiteHelper extends SQLiteOpenHelper {
    public static final String COL_BOOK = "BOOKS";
    public static final String COL_CHAPTER = "CHAPTERS";
    public static final String COL_COLOR = "COLORS";
    public static final String COL_VERSE = "VERSES";
    public static final String COL_VERSENO = "V_NO";
    private static final String DB_CREATE = "CREATE TABLE BOOKMARKS_TABLE ( ID integer primary key autoincrement, BOOKS, CHAPTERS, V_NO, VERSES, COLORS INTEGER)";
    private static final String DB_NAME = "BOOKMARKS_DB";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "BOOKMARKS_TABLE";
    public static SQLiteDatabase sqLiteDatabase;

    public NIVMySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        db.execSQL(DB_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BOOKMARKS_TABLE");
        db.execSQL(DB_CREATE);
    }
}