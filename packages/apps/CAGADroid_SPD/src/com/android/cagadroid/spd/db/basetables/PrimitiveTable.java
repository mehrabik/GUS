package com.android.cagadroid.spd.db.basetables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public abstract class PrimitiveTable implements BaseColumns {
    public static String COLUMN_NAME_ID = "ID";
    public static final String COLUMN_NAME_NAME = "Name";

    public abstract String tableName();

    public class Tuple {
        public Integer id;
        public String name;
    }

    public List<Tuple> getAll(SQLiteDatabase db) {
        String[] projection = {
                COLUMN_NAME_ID, COLUMN_NAME_NAME
        };

        Cursor cursor = db.query(
                tableName(),
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Tuple> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            Tuple a = new Tuple();
            a.id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
            a.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
            itemIds.add(a);
        }
        cursor.close();
        return itemIds;
    }

    public String getCreateStatement() {
       return "CREATE TABLE IF NOT EXISTS " + tableName() + " ( "
                + COLUMN_NAME_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_NAME + " TEXT NOT NULL, "
                + "PRIMARY KEY(" + COLUMN_NAME_ID + ")"
                + ");";
    }

    public String getDeleteStatement() {
        return "DROP TABLE IF EXISTS " + tableName() + ";";
    }

    public void insert(SQLiteDatabase db, Integer id, String name) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, id);
        values.put(COLUMN_NAME_NAME, name);
        db.insert(tableName(), null, values);
    }

    public  String getNameById(SQLiteDatabase db, Integer Id) {
        String[] projection = {
                COLUMN_NAME_NAME
        };

        String selection = COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {Id.toString()};

        Cursor cursor = db.query(
                tableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst() == false)
            return "";
        String output = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
        cursor.close();
        return output;
    }

    public Integer getIdByName(SQLiteDatabase db, String name) {
        String[] projection = {
                COLUMN_NAME_ID
        };

        String selection = COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(
                tableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst() == false) {
            return 0;
        }
        Integer output = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
        return output;
    }
}
