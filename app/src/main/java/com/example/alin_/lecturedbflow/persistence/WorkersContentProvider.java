package com.example.alin_.lecturedbflow.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


/**
 * Created by alin- on 15.11.2017.
 */

public class WorkersContentProvider extends ContentProvider {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    /*
        Database Info
     */
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    public static final String AUTHORITY = "com.example.alin.lecturedbflow";
    /*
        Workers table info
     */
    public static final String WORKERS_TABLE = "Worker";
    public static final String WORKER_ID = "id";
    public static final String WORKER_NAME = "name";
    public static final String WORKER_BIRTHDAY = "birthday";
    public static final String WORKER_PATH = "workers";
    public static final Uri WORKER_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + WORKER_PATH);
    static final String WORKER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + WORKER_PATH;
    static final String WORKER_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + WORKER_PATH;
    public static final int URI_WORKERS = 1;
    public static final int URI_WORKER_ID = 2;
    /*
        Company table info
     */
    public static final String COMPANY_TABLE = "Company";
    public static final String COMPANY_ID = "id";
    public static final String COMPANY_NAME = "name";
    public static final String COMPANY_LOCATION = "location";
    public static final String COMPANY_EMAIL = "email";
    public static final String COMPANY_PATH = "companies";
    public static final Uri COMPANY_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + COMPANY_PATH);
    static final String COMPANY_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + COMPANY_PATH;
    static final String COMPANY_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + COMPANY_PATH;
    public static final int URI_COMPANIES = 3;
    public static final int URI_COMPANY_ID = 4;
    /*
        UriMatcher info
     */
    private static final UriMatcher uriMatcher;
    public static final String DB_CREATE = "create table " + WORKERS_TABLE + "("
            + WORKER_ID + " integer primary key autoincrement, "
            + WORKER_NAME + " text, "
            + WORKER_BIRTHDAY + " integer" + ");" +
            "create table " + COMPANY_TABLE + " ("
            + COMPANY_ID + " integer primary key autoincrement, "
            + COMPANY_NAME + " text, "
            + COMPANY_LOCATION + " text, "
            + COMPANY_EMAIL + " text);";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, WORKER_PATH, URI_WORKERS);
        uriMatcher.addURI(AUTHORITY, WORKER_PATH + "/#", URI_WORKER_ID);
        uriMatcher.addURI(AUTHORITY, COMPANY_PATH, URI_COMPANIES);
        uriMatcher.addURI(AUTHORITY, COMPANY_PATH + "/#", URI_COMPANY_ID);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        ;
        String selectedTable;
        switch (uriMatcher.match(uri)) {
            case URI_WORKERS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = WORKER_NAME + " ASC";
                }
                selectedTable = WORKERS_TABLE;
                break;
            case URI_WORKER_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = WORKER_ID + " =" + id;
                } else {
                    selection = selection + " AND " + WORKER_ID + " = " + id;
                }
                selectedTable = WORKERS_TABLE;
                break;
            case URI_COMPANIES:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = COMPANY_NAME + " ASC";
                }
                selectedTable = COMPANY_TABLE;
                break;
            case URI_COMPANY_ID:
                String companyId = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = COMPANY_ID + " =" + companyId;
                }
                selectedTable = COMPANY_TABLE;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(selectedTable, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_WORKERS:
                return WORKER_CONTENT_TYPE;
            case URI_WORKER_ID:
                return WORKER_CONTENT_ITEM_TYPE;
            case URI_COMPANIES:
                return COMPANY_CONTENT_TYPE;
            case URI_COMPANY_ID:
                return COMPANY_CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String selectedTable;

        switch (uriMatcher.match(uri)) {
            case URI_WORKERS:
                selectedTable = WORKERS_TABLE;
                break;
            case URI_COMPANIES:
                selectedTable = COMPANY_TABLE;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        long rowId = database.insert(selectedTable, null, values);
        Uri resultUri = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String selectedTable;
        switch (uriMatcher.match(uri)) {
            case URI_WORKERS:
                selectedTable = WORKERS_TABLE;
                break;
            case URI_WORKER_ID:
                selectedTable = WORKERS_TABLE;
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = WORKER_ID + " =" + id;
                } else {
                    selection = selection + " AND " + WORKER_ID + " = " + id;
                }
                break;
            case URI_COMPANIES:
                selectedTable = COMPANY_TABLE;
                break;
            case URI_COMPANY_ID:
                selectedTable = COMPANY_TABLE;
                String companyId = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = COMPANY_ID + " =" + companyId;
                } else {
                    selection = selection + " AND " + COMPANY_ID + " = " + companyId;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        database = dbHelper.getWritableDatabase();
        int count = database.delete(selectedTable, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String selectedTable;
        switch (uriMatcher.match(uri)) {
            case URI_WORKERS:
                selectedTable = WORKERS_TABLE;
                break;
            case URI_WORKER_ID:
                selectedTable = WORKERS_TABLE;
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = WORKER_ID + " =" + id;
                } else {
                    selection = selection + " AND " + WORKER_ID + " = " + id;
                }
                break;
            case URI_COMPANIES:
                selectedTable = COMPANY_TABLE;
                break;
            case URI_COMPANY_ID:
                selectedTable = COMPANY_TABLE;
                String companyId = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = COMPANY_ID + " =" + companyId;
                } else {
                    selection = selection + " AND " + COMPANY_ID + " = " + companyId;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        database = dbHelper.getWritableDatabase();
        int count = database.update(selectedTable, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
