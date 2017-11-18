package com.example.alin_.lecturedbflow.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.alin_.lecturedbflow.persistence.WorkersContentProvider.DB_CREATE;

/**
 * Created by alin- on 15.11.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, WorkersContentProvider.DATABASE_NAME, null, WorkersContentProvider.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
