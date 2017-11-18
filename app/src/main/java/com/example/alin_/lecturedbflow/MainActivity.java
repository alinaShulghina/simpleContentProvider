package com.example.alin_.lecturedbflow;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.alin_.lecturedbflow.persistence.Worker;
import com.example.alin_.lecturedbflow.persistence.WorkersContentProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Worker> workers = retreiveDataFromDB();
        Adapter adapter = new Adapter(workers);
        RecyclerView recyclerView = findViewById(R.id.rv_workers);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private ArrayList<Worker> retreiveDataFromDB() {
        ArrayList<Worker> workers = new ArrayList<>();
        Cursor cursor = getContentResolver().query(WorkersContentProvider.WORKER_CONTENT_URI,
                null,null,null,null);
        if (cursor == null) return null;
        else{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String workerName = cursor.getString(cursor.getColumnIndex(WorkersContentProvider.WORKER_NAME));
                long workerBirthday = cursor.getLong(cursor.getColumnIndex(WorkersContentProvider.WORKER_BIRTHDAY));
                workers.add(new Worker(workerName,workerBirthday));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return workers;
    }

    public void onClickInsert(View v) {
        ContentValues cv = new ContentValues();
        cv.put(WorkersContentProvider.WORKER_NAME, "Fucking worker name");
        cv.put(WorkersContentProvider.WORKER_BIRTHDAY, "345678901");
        getContentResolver().insert(WorkersContentProvider.WORKER_CONTENT_URI, cv);
    }

    public void onClickUpdate(View v) {
        ContentValues cv = new ContentValues();
        cv.put(WorkersContentProvider.WORKER_NAME, "Fucking worker name2");
        cv.put(WorkersContentProvider.WORKER_BIRTHDAY, "12345");
        Uri uri = ContentUris.withAppendedId(WorkersContentProvider.WORKER_CONTENT_URI, 2);
        getContentResolver().update(uri, cv, null, null);
    }

    public void onClickDelete(View v) {
        Uri uri = ContentUris.withAppendedId(WorkersContentProvider.WORKER_CONTENT_URI, 3);
        getContentResolver().delete(uri, null, null);
    }



}
