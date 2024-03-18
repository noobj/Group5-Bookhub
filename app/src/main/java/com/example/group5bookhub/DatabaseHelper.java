package com.example.group5bookhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME ="Bookhub.db";
    final static int DATABASE_VERSION = 1;
    final static String TABLE1_NAME = "User";
    final static String T1COL1 = "ID";
    final static String T1COL2 = "Username";
    final static String T1COL3 = "Password";
    final static String T1COL4 = "Email";
    final static String T1COL5 = "Address";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE1_NAME
                + "(" + T1COL1 + " INTEGER PRIMARY KEY,"
                + T1COL2 + " TEXT, "
                + T1COL3 + " TEXT,"
                + T1COL4 + " TEXT,"
                + T1COL5 + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String username,String password,String email,String address){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL2,username);
        values.put(T1COL3,password);
        values.put(T1COL4,email);
        values.put(T1COL5,address);
        long l = sqLiteDatabase.insert(TABLE1_NAME,null,values);
        if(l>0)
            return true;
        else
            return false;
    }

    public Cursor getUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public Cursor getUserByEmail(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // Enclose email value in single quotes
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE Email='" + email + "'";
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public boolean deleteUser(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        /*String query = "DELETE FROM " + TABLE1_NAME + " WHERE ID = " + id;
        sqLiteDatabase.execSQL(query);*/

        int d = sqLiteDatabase.delete(TABLE1_NAME,"ID=?",
                new String[]{Integer.toString(id)});
        if(d>0)
            return true;
        else
            return false;
    }

    public boolean updateUserEmail(int id,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL4,email);
        int u = sqLiteDatabase.update(TABLE1_NAME,values,"id=?",
                new String[]{Integer.toString(id)});
        if(u>0)
            return true;
        else
            return false;

    }
}
