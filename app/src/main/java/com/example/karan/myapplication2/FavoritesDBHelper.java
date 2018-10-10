package com.example.karan.myapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.karan.myapplication2.model.TopSources;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by karan on 4/19/2018.
 */

public class FavoritesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Favorites";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "text";

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean newFavorite(TopSources topSources) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, topSources.getName());

        long result = database.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else return true;

    }

    public List<TopSources> favoritesList() {
        List<TopSources> topSources = new LinkedList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        TopSources topSources1;

        if (cursor.moveToFirst()) {
            do {
                topSources1 = new TopSources();

                topSources1.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                topSources1.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));

                topSources.add(topSources1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topSources;
    }
}
