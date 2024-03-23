package com.example.group5bookhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME ="Bookhub.db";
    final static int DATABASE_VERSION = 2;
    final static String TABLE1_NAME = "User";
    final static String T1COL1 = "ID";
    final static String T1COL2 = "Username";
    final static String T1COL3 = "Password";
    final static String T1COL4 = "Email";
    final static String T1COL5 = "Address";


    final static String TABLE_BOOK = "Book";
    final static String BOOK_ID = "ID";
    final static String BOOK_TITLE = "Title";
    final static String BOOK_AUTHOR = "Author";
    final static String BOOK_DESC = "Description";
    final static String BOOK_PRICE = "Price";
    final static String BOOK_FOR_SALE = "IsForSale";
    final static String BOOK_SELLER = "SellerId";
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


        query = "CREATE TABLE " + TABLE_BOOK
                + "(" + BOOK_ID + " INTEGER PRIMARY KEY,"
                + BOOK_TITLE + " TEXT, "
                + BOOK_AUTHOR + " TEXT,"
                + BOOK_DESC + " TEXT,"
                + BOOK_PRICE + " FLOAT,"
                + BOOK_FOR_SALE + " TINYINT,"
                + BOOK_SELLER + " INTEGER,"
                + " FOREIGN KEY ("+BOOK_SELLER+") REFERENCES "+TABLE1_NAME+"("+T1COL1+"));";;
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
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
        return l > 0;
    }

    public Cursor getUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME;
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor getUserByEmail(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // Enclose email value in single quotes
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE Email='" + email + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getUserById(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE ID='" + id + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public boolean deleteUser(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        /*String query = "DELETE FROM " + TABLE1_NAME + " WHERE ID = " + id;
        sqLiteDatabase.execSQL(query);*/

        int d = sqLiteDatabase.delete(TABLE1_NAME,"ID=?",
                new String[]{Integer.toString(id)});
        return d > 0;
    }

    public boolean updateUserEmail(int id,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL4,email);
        int u = sqLiteDatabase.update(TABLE1_NAME,values,"id=?",
                new String[]{Integer.toString(id)});
        return u > 0;
    }

    public boolean insertBook(String title,String author,String desc,float price, boolean isForSale, int sellerId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_AUTHOR, author);
        values.put(BOOK_DESC, desc);
        values.put(BOOK_SELLER, sellerId);
        values.put(BOOK_PRICE, price);
        values.put(BOOK_TITLE, title);
        values.put(BOOK_FOR_SALE, isForSale);
        long l = sqLiteDatabase.insert(TABLE_BOOK,null,values);
        return l > 0;
    }

    public Cursor getBooks(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK;
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor getBookBySellerId(int sellerId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE SellerId='" + sellerId + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }
}
