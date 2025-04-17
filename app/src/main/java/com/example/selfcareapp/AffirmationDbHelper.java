package com.example.selfcareapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AffirmationDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "affirmations.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_AFFIRMATIONS = "affirmations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_CATEGORY = "category";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_AFFIRMATIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TEXT
            + " text not null, " + COLUMN_CATEGORY + " text);";

    public AffirmationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        // Add some initial affirmations
        addInitialAffirmations(database);
    }

    private void addInitialAffirmations(SQLiteDatabase db) {
        // Create some sample affirmations
        String[] affirmations = {
                "I am worthy of love and respect",
                "I believe in my abilities",
                "I am in charge of my life",
                "I am getting stronger every day",
                "I have the power to create change",
                // Add more as needed
        };

        // Insert each affirmation
        for (String affirmation : affirmations) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TEXT, affirmation);
            values.put(COLUMN_CATEGORY, "general");
            db.insert(TABLE_AFFIRMATIONS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFFIRMATIONS);
        onCreate(db);
    }
}
