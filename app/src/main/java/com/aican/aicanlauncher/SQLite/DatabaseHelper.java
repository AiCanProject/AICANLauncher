package com.aican.aicanlauncher.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Appdata.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Appdetails(appId TEXT PRIMARY KEY, appName TEXT, appLogo BLOB, type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Appdetails");

    }

    public boolean insertAppData(String appId, String appName, byte[] appLogo, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("appId", appId);
        contentValues.put("appName", appName);
        contentValues.put("appLogo", appLogo);
        contentValues.put("type", type);
        long result = db.insert("Appdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean deleteAppdata(String appID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Appdetails", null);
        if (cursor.getCount() > 0) {
            long result = db.delete("Appdetails", "appId=?", new String[]{appID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkAlreadyExist(String appId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  appId  FROM  Appdetails  WHERE " + "appId" + "  =?";
        Cursor cursor = db.rawQuery(query, new String[]{appId});
        return cursor.getCount() <= 0;
    }

    public Boolean check(String appId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Appdetails", null);
        if (cursor.getCount() > 0) {
            long result = db.delete("Appdetails", "appId=?", new String[]{appId});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean checkDelete(String appId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Appdetails", null);
        if (cursor.getCount() > 0) {
            long result = db.delete("Appdetails", "appId=?", new String[]{appId});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
