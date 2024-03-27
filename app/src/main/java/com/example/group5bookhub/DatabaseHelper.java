package com.example.group5bookhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Random;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME ="Bookhub.db";
    final static int DATABASE_VERSION = 4;
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

    final static String BOOK_IMAGE = "Image";
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
                + BOOK_IMAGE + " TEXT,"
                + " FOREIGN KEY ("+BOOK_SELLER+") REFERENCES "+TABLE1_NAME+"("+T1COL1+"));";
        sqLiteDatabase.execSQL(query);
//        query = "Insert into " + TABLE1_NAME
//                + "("
//                + T1COL2 + ","
//                + T1COL3 + ","
//                + T1COL4 + ","
//                + T1COL5 + ") Values (" +
//                "'admin',12345,'admin@gmail.com','Douglas College')";
//
//        sqLiteDatabase.execSQL(query);
//
//
//
//        query = "Insert into " + TABLE_BOOK
//                + "("
//                + BOOK_AUTHOR + ","
//                + BOOK_DESC + ","
//                + BOOK_SELLER + ","
//                + BOOK_PRICE + ","
//                + BOOK_TITLE + ","
//                + BOOK_FOR_SALE + ","
//                + BOOK_IMAGE +
//                ") Values (" +
//                "'Tony Faggioli','As our story unfolds, it becomes clear that there is hell, and then there is hell on earth.',1,18.99,'A Million to One: Beyond'," +
//                "1, 'bookcover2'),('Jennifer L. Armentrout','Samantha is a stranger in her own life.',1,8.99,'Don''t Look Back',1, 'bookcover1')";
//
//        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(sqLiteDatabase);
    }

    public void insertFakeBooks(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_AUTHOR, "Tony Faggioli");
        values.put(BOOK_DESC, "As our story unfolds, it becomes clear that there is hell, and then there is hell on earth.\n" +
                "And worst of all...there are some demons you never see coming.\n" +
                "\n" +
                "Detective Napoleon Villa’s latest case is spiraling out of control. Drawing on his skills, he battles to save former suspect Kyle Fasano from becoming the next victim.");
        values.put(BOOK_SELLER, 1);
        values.put(BOOK_PRICE, 18.99);
        values.put(BOOK_TITLE, "A Million to One: Beyond");
        values.put(BOOK_FOR_SALE, 1);
        values.put(BOOK_IMAGE, "bookcover2");
        sqLiteDatabase.insert(TABLE_BOOK,null,values);
        ContentValues values1 = new ContentValues();
        values1.put(BOOK_AUTHOR, "Jennifer L. Armentrout");
        values1.put(BOOK_DESC, "Samantha is a stranger in her own life. Until the night she disappeared with her best friend, Cassie, everyone said Sam had it all - popularity, wealth, and a dream boyfriend.\n" +
                "\n" +
                "Sam has resurfaced, but she has no recollection of who she was or what happened to her that night. As she tries to piece together her life from before, she realizes it's one she no longer wants any part of. The old Sam took \"mean girl\" to a whole new level, and it's clear she and Cassie were more like best enemies. Sam is pretty sure that losing her memories is like winning the lottery. She's getting a second chance at being a better daughter, sister, and friend, and she's falling hard for Carson Ortiz, a boy who has always looked out for her-even if the old Sam treated him like trash.");
        values1.put(BOOK_SELLER, 1);
        values1.put(BOOK_PRICE, 8.99);
        values1.put(BOOK_TITLE, "Don't Look Back");
        values1.put(BOOK_FOR_SALE, 1);
        values1.put(BOOK_IMAGE, "bookcover1");
        sqLiteDatabase.insert(TABLE_BOOK,null,values1);
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
        Random rand = new Random();
        int upperbound = 5;
        int int_random = rand.nextInt(upperbound) + 1;

        values.put(BOOK_IMAGE, "bookcover" + int_random);
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

    public Cursor getBookById(int bookId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE " + BOOK_ID + "=?";
        return sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(bookId)});
    }

    public boolean updateUserDetails(int userId,String userName,String userAddress,String userEmail){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL2,userName);
        values.put(T1COL5,userAddress);
        values.put(T1COL4,userEmail);
        int u = sqLiteDatabase.update(TABLE1_NAME,values,"id=?",
                new String[]{Integer.toString(userId)});
        return u > 0;
    }
}
