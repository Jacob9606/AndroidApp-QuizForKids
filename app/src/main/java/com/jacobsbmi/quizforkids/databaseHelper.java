package com.jacobsbmi.quizforkids;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "user.db";

    // User table info
    public static final String TABLE_USER = "user_table";
    public static final String COLUMN_USER_NAME = "User_Name";
    private static final String COLUMN_USER_ID = "ID";
    private static final String COLUMN_USER_PASSWORD = "PASSWORD";


    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_NAME + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropUserTable = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(dropUserTable);

        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_NAME + " TEXT)";
        db.execSQL(createUserTable);
    }

    public boolean addData(String id, String password, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, id);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_NAME, userName);
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return (result != -1);
    }


    public boolean checkPassword(String currentPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{currentPassword});
        boolean passwordExists = (cursor.getCount() > 0);
        cursor.close();
        db.close(); // 추가: 데이터베이스 연결 종료
        return passwordExists;
    }

    public boolean updatePassword(String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, newPassword); // 수정: "password" -> COLUMN_USER_PASSWORD
        int rowsAffected = db.update(TABLE_USER, values, null, null);
        db.close(); // 추가: 데이터베이스 연결 종료
        return (rowsAffected > 0);
    }

    public boolean checkLogin(String id, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER_PASSWORD};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(
                TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean loginSuccess = false; // 수정: 기본값 false로 설정
        if (cursor.moveToFirst()) {
            String passwordFromDB = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
            loginSuccess = passwordFromDB.equals(password);
        }
        cursor.close();
        db.close(); // 추가: 데이터베이스 연결 종료
        return loginSuccess;
    }
}
