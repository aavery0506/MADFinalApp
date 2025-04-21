package com.example.selfcareapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Helper class for storing and retrieving breathing data
public class GoalDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "breathing_goals.db";
    private static final int DATABASE_VERSION = 1;

    //table names
    private static final String TABLE_GOALS = "goals";
    private static final String TABLE_SESSIONS = "sessions";

    //common column name
    private static final String KEY_ID = "id";

    // Goals table columns
    private static final String KEY_TYPE = "type";
    private static final String KEY_TARGET_MINUTES = "target_minutes";
    private static final String KEY_IS_ACTIVE = "is_active";
    private static final String KEY_CREATED_DATE = "created_date";

    // Sessions table columns
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_EXERCISE_TYPE = "exercise_type";

    // Create tables
    private static final String CREATE_GOALS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TYPE + " TEXT,"
            + KEY_TARGET_MINUTES + " INTEGER,"
            + KEY_IS_ACTIVE + " INTEGER,"
            + KEY_CREATED_DATE + " TEXT" + ")";

    private static final String CREATE_SESSIONS_TABLE = "CREATE TABLE " + TABLE_SESSIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TIMESTAMP + " TEXT,"
            + KEY_DURATION + " INTEGER,"
            + KEY_EXERCISE_TYPE + " TEXT" + ")";

    //constructor
    public GoalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        db.execSQL(CREATE_GOALS_TABLE);
        db.execSQL(CREATE_SESSIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        onCreate(db);
    }

    //Methods to add, update, delete, and query goals and sessions

    // Add a new goal
    public long addGoal(GoalModel goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, goal.getType().toString());
        values.put(KEY_TARGET_MINUTES, goal.getTargetMinutes());
        values.put(KEY_IS_ACTIVE, goal.isActive() ? 1 : 0);
        values.put(KEY_CREATED_DATE, goal.getCreatedDate().toString());

        long id = db.insert(TABLE_GOALS, null, values);
        db.close();
        return id;
    }

    // Add a new session
    public long addSession(SessionRecord session) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TIMESTAMP, session.getTimeStamp().toString());
        values.put(KEY_DURATION, session.getDurationMinutes());
        values.put(KEY_EXERCISE_TYPE, session.getExerciseType());

        long id = db.insert(TABLE_SESSIONS, null, values);
        db.close();
        return id;
    }

    //get all active goals
    public List<GoalModel> getActiveGoals() {
        List<GoalModel> goalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_GOALS + " WHERE " + KEY_IS_ACTIVE + "=1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GoalModel goal = new GoalModel(
                        GoalModel.GoalType.valueOf(cursor.getString(cursor.getColumnIndex(KEY_TYPE))),
                        cursor.getInt(cursor.getColumnIndex(KEY_TARGET_MINUTES))
                );
                goal.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                goal.setActive(cursor.getInt(cursor.getColumnIndex(KEY_IS_ACTIVE)) == 1);
                goal.setCreatedDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(KEY_CREATED_DATE))));

                goalList.add(goal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return goalList;
    }

    // Get sessions for a specific date range
    public List<SessionRecord> getSessionsInRange(LocalDate startDate, LocalDate endDate) {
        List<SessionRecord> sessionList = new ArrayList<>();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        String selectQuery = "SELECT * FROM " + TABLE_SESSIONS +
                " WHERE datetime(" + KEY_TIMESTAMP + ") >= datetime('" + startDateTime + "')" +
                " AND datetime(" + KEY_TIMESTAMP + ") <= datetime('" + endDateTime + "')";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SessionRecord session = new SessionRecord(
                        cursor.getInt(cursor.getColumnIndex(KEY_DURATION)),
                        cursor.getString(cursor.getColumnIndex(KEY_EXERCISE_TYPE))
                );
                session.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                session.setTimeStamp(LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP))));

                sessionList.add(session);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sessionList;
    }

}
