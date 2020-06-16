package com.android.cagadroid.spd.db.basetables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public abstract class PolicyTable {
    public static final String TABLE_NAME = "policies";
    public static final String COLUMN_NAME_ACTION_ID = "ActionID";
    public static final String COLUMN_NAME_DOMAIN_ID = "DomainID";
    public static final String COLUMN_NAME_TYPE_ID = "TypeID";
    public static final String COLUMN_NAME_OPERATION_ID = "OperationID";
    public static final String COLUMN_NAME_ROLE_ID = "RoleID";
    public static final String COLUMN_NAME_MODALITY_ID = "ModalityID";
    public static final String COLUMN_NAME_CONF_LEVEL = "Conf";

    public class Decision {
        public Integer actionId;
        public Double confidence;
    }

    public class PolicyRep {
        public Integer ActionID;
        public Integer DomainID;
        public Integer TypeID;
        public Integer OperationID;
        public Integer RoleID;
        public Integer ModalityID;
        public Double Confidence;
    }

    public String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COLUMN_NAME_ACTION_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_DOMAIN_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_TYPE_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_OPERATION_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_ROLE_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_MODALITY_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME_CONF_LEVEL + " REAL NOT NULL, "
                + "PRIMARY KEY("
                + COLUMN_NAME_DOMAIN_ID + ","
                + COLUMN_NAME_TYPE_ID + ","
                + COLUMN_NAME_OPERATION_ID + ","
                + COLUMN_NAME_ROLE_ID + ","
                + COLUMN_NAME_MODALITY_ID  + ")"
                + ");";
    }

    public String getDeleteStatement() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    public List<PolicyRep> getAll(SQLiteDatabase db) {
        String[] projection = {
                COLUMN_NAME_ACTION_ID, COLUMN_NAME_DOMAIN_ID,
                COLUMN_NAME_TYPE_ID, COLUMN_NAME_OPERATION_ID,
                COLUMN_NAME_ROLE_ID, COLUMN_NAME_MODALITY_ID,
                COLUMN_NAME_CONF_LEVEL
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<PolicyRep> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            PolicyRep a = new PolicyRep();
            a.ActionID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ACTION_ID));
            a.DomainID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DOMAIN_ID));
            a.TypeID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TYPE_ID));
            a.OperationID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_OPERATION_ID));
            a.RoleID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ROLE_ID));
            a.ModalityID = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MODALITY_ID));
            a.Confidence = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_CONF_LEVEL));
            itemIds.add(a);
        }
        cursor.close();
        return itemIds;

    }

    public void insert(SQLiteDatabase db, Integer Action_ID, Integer Domain_ID,
                              Integer Type_ID, Integer Operation_ID,
                              Integer Role_ID, Integer Modality_ID,
                              Double Conf_Level) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ACTION_ID, Action_ID);
        values.put(COLUMN_NAME_DOMAIN_ID, Domain_ID);
        values.put(COLUMN_NAME_TYPE_ID, Type_ID);
        values.put(COLUMN_NAME_OPERATION_ID, Operation_ID);
        values.put(COLUMN_NAME_ROLE_ID, Role_ID);
        values.put(COLUMN_NAME_MODALITY_ID, Modality_ID);
        values.put(COLUMN_NAME_CONF_LEVEL, Conf_Level);
        db.insert(TABLE_NAME, null, values);
    }

    public Decision checkPolicy(SQLiteDatabase db, Integer Domain_ID,
                                       Integer Type_ID, Integer Operation_ID,
                                       Integer Role_ID, Integer Modality_ID) {

        String[] projection = {
                COLUMN_NAME_ACTION_ID, COLUMN_NAME_CONF_LEVEL
        };

        String selection = "";
        Boolean first_expression = true;
        
        selection += COLUMN_NAME_DOMAIN_ID + " = ?";
        selection += " AND ";
        selection += COLUMN_NAME_TYPE_ID + " = ?";
        selection += " AND ";
        selection += COLUMN_NAME_OPERATION_ID + " = ?";
        selection += " AND ";
        selection += COLUMN_NAME_ROLE_ID + " = ?";
        selection += " AND ";
        selection += COLUMN_NAME_MODALITY_ID + " = ?";

        String[] selectionArgs = {
                Domain_ID.toString(),
                Type_ID.toString(),
                Operation_ID.toString(),
                Role_ID.toString(),
                Modality_ID.toString()
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst() == false)
            return null;
        Decision output = new Decision();
        output.actionId = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ACTION_ID));
        output.confidence = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_CONF_LEVEL));
        cursor.close();
        return output;
    }
}
