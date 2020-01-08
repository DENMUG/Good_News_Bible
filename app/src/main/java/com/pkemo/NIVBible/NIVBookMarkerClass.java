package com.pkemo.NIVBible;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Nelson on 1/14/2017.
 */

public class NIVBookMarkerClass {
    private static String[] allColumns;
    public static SQLiteDatabase database;
    private NIVMySQLiteHelper dbHelper;

    static {
        allColumns = new String[]{"ID", NIVMySQLiteHelper.COL_BOOK, NIVMySQLiteHelper.COL_CHAPTER, NIVMySQLiteHelper.COL_VERSENO, NIVMySQLiteHelper.COL_VERSE, NIVMySQLiteHelper.COL_COLOR};
    }

    public NIVBookMarkerClass(Context context) {
        this.dbHelper = new NIVMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = this.dbHelper.getWritableDatabase();
    }

    public void close() {
        this.dbHelper.close();
    }

    public static long setBookMarks(String book_name, String chapter, String vno, String verse, int color_code) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NIVMySQLiteHelper.COL_BOOK, book_name);
        initialValues.put(NIVMySQLiteHelper.COL_CHAPTER, chapter);
        initialValues.put(NIVMySQLiteHelper.COL_VERSENO, vno);
        initialValues.put(NIVMySQLiteHelper.COL_VERSE, verse);
        initialValues.put(NIVMySQLiteHelper.COL_COLOR, Integer.valueOf(color_code));
        return database.insert(NIVMySQLiteHelper.TABLE_NAME, null, initialValues);
    }

    public static ArrayList<NIVBookMark> getAllComments() {
        ArrayList<NIVBookMark> bookmarks = new ArrayList();
        Cursor cursor = database.query(NIVMySQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bookmarks.add(cursorToBookMark(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return bookmarks;
    }

    public static int getMeColor(String b_name, String ch, String vno) {
        int color = 0;
        Cursor cursor = database.query(NIVMySQLiteHelper.TABLE_NAME, allColumns, "BOOKS = ? AND CHAPTERS = ? AND CAST(V_NO AS TEXT) = ?", new String[]{b_name, ch, vno}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0 && cursor != null) {
            color = cursor.getInt(cursor.getColumnIndex(NIVMySQLiteHelper.COL_COLOR));
        }
        cursor.close();
        return color;
    }

    public static void deleteComment(int id) {
        database.delete(NIVMySQLiteHelper.TABLE_NAME, "ID = " + id, null);
    }

    private static NIVBookMark cursorToBookMark(Cursor cursor) {
        NIVBookMark NIVBookMark = new NIVBookMark();
        NIVBookMark.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        NIVBookMark.setBookname(cursor.getString(cursor.getColumnIndex(NIVMySQLiteHelper.COL_BOOK)));
        NIVBookMark.setChapter_no(cursor.getString(cursor.getColumnIndex(NIVMySQLiteHelper.COL_CHAPTER)));
        NIVBookMark.setB_versenumber(cursor.getString(cursor.getColumnIndex(NIVMySQLiteHelper.COL_VERSENO)));
        NIVBookMark.setVerse(cursor.getString(cursor.getColumnIndex(NIVMySQLiteHelper.COL_VERSE)));
        NIVBookMark.setColorcode(cursor.getInt(cursor.getColumnIndex(NIVMySQLiteHelper.COL_COLOR)));
        return NIVBookMark;
    }
}
