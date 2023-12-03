package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AnimalImageDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AnimalImages.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AnimalImageContract.AnimalImageEntry.TABLE_NAME + " (" +
                    AnimalImageContract.AnimalImageEntry._ID + " INTEGER PRIMARY KEY," +
                    AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AnimalImageContract.AnimalImageEntry.TABLE_NAME;

    public AnimalImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        insertInitialData(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        String[] animals = {
                "cow",
                "dear",
                "dog",
                "elephant",
                "kangroo",
                "koala",
                "lion",
                "pig",
                "squirrel",
                "tiger"
        };

        for (String animal : animals) {
            insertAnimal(db, animal);
        }
    }

    private void insertAnimal(SQLiteDatabase db, String filename) {
        String sql = "INSERT INTO " + AnimalImageContract.AnimalImageEntry.TABLE_NAME +
                " (" +
                AnimalImageContract.AnimalImageEntry.COLUMN_NAME_FILENAME +
                ") VALUES (?)";
        db.execSQL(sql, new String[]{filename});
    }

}
