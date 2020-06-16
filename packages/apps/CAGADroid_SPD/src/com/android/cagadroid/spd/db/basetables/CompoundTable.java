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
public abstract class CompoundTable implements BaseColumns {
    public abstract String individualIdName();
    public abstract String groupIdName();
    public abstract String tableName();

    public class Assignment {
        public Integer individualId;
        public Integer groupId;
    }

    public String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS " + tableName() + " ( "
                + individualIdName() + " INTEGER NOT NULL, "
                + groupIdName() + " INTEGER NOT NULL, "
                + "PRIMARY KEY(" + individualIdName() + "," + groupIdName() + ")"
                + ");";
    }

    public String getDeleteStatement() {
        return "DROP TABLE IF EXISTS " + tableName() + ";";
    }

    public void insert(SQLiteDatabase db, Integer id1, Integer id2) {
        ContentValues values = new ContentValues();
        values.put(individualIdName(), id1);
        values.put(groupIdName(), id2);
        db.insert(tableName(), null, values);
    }

    public List<Assignment> getAll(SQLiteDatabase db) {
        String[] projection = {
                individualIdName(), groupIdName()
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

        List<Assignment> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            Assignment a = new Assignment();
            a.individualId = cursor.getInt(cursor.getColumnIndex(individualIdName()));
            a.groupId = cursor.getInt(cursor.getColumnIndex(groupIdName()));
            itemIds.add(a);
        }
        cursor.close();
        return itemIds;
    }

    public List<Integer> getGroupsofIndividual(SQLiteDatabase db, Integer individualId) {
        String[] projection = {
                groupIdName()
        };

        String selection = individualIdName() + " = ?";
        String[] selectionArgs = {individualId.toString()};

        Cursor cursor = db.query(
                tableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<Integer> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            Integer itemId = cursor.getInt(cursor.getColumnIndex(groupIdName()));
            itemIds.add(itemId);
        }
        itemIds.add(0);

        cursor.close();
        return itemIds;
    }

    public List<Integer> getIndividualsInGroup(SQLiteDatabase db, Integer groupId) {
        String[] projection = {
                individualIdName()
        };

        String selection = groupIdName() + " = ?";
        String[] selectionArgs = {groupId.toString()};

        Cursor cursor = db.query(
                tableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<Integer> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            Integer itemId = cursor.getInt(cursor.getColumnIndex(individualIdName()));
            itemIds.add(itemId);
        }
        itemIds.add(0);

        cursor.close();
        return itemIds;
    }

}
