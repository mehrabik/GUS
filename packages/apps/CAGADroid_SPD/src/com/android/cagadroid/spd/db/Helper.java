package com.android.cagadroid.spd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Helper extends SQLiteOpenHelper {

    public static final String TAG = "CAGA.SPD";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = null; // to use in-memory DB

    private Context mContext;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "Creating CAGADroid Policy DB");
   
        db.execSQL(Contract.Action.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Operation.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Domain.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Type.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Role.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Modality.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Subject.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Object.INSTANCE.getCreateStatement());
        db.execSQL(Contract.User.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Authenticator.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Object_Type.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Subject_Domain.INSTANCE.getCreateStatement());
        db.execSQL(Contract.User_Role.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Authenticator_Modality.INSTANCE.getCreateStatement());
        db.execSQL(Contract.Policy.INSTANCE.getCreateStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDestroy(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onDestroy(SQLiteDatabase db) {
        Log.v(TAG, "Deleting CAGADroid Policy DB");
   
        db.execSQL(Contract.Action.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Operation.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Domain.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Type.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Role.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Modality.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Subject.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Object.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.User.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Authenticator.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Object_Type.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Subject_Domain.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.User_Role.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Authenticator_Modality.INSTANCE.getDeleteStatement());
        db.execSQL(Contract.Policy.INSTANCE.getDeleteStatement());
    }
}
