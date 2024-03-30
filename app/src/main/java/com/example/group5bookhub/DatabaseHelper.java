package com.example.group5bookhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Random;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME ="Bookhub.db";
    final static int DATABASE_VERSION = 8;
    final static String TABLE1_NAME = "Users";
    final static String T1COL1 = "ID";
    final static String T1COL2 = "Username";
    final static String T1COL3 = "Password";
    final static String T1COL4 = "Email";
    final static String T1COL5 = "Address";


    final static String TABLE_BOOK = "Books";
    final static String BOOK_ID = "ID";
    final static String BOOK_TITLE = "Title";
    final static String BOOK_AUTHOR = "Author";
    final static String BOOK_DESC = "Description";
    final static String BOOK_PRICE = "Price";
    final static String BOOK_FOR_SALE = "IsForSale";
    final static String BOOK_SELLER = "SellerId";
    final static String BOOK_IMAGE = "Image";


    final static String TABLE_ORDER = "Orders";
    final static String ORDER_ID = "ID";
    final static String ORDER_BOOK = "BookId";
    final static String ORDER_SELLER = "SellerId";
    final static String ORDER_BUYER = "BuyerId";
    final static String ORDER_DATE = "Date";
    final static String ORDER_ADDRESS = "Address";
    final static String ORDER_STATUS = "Status";

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

        query = "CREATE TABLE " + TABLE_ORDER
                + "(" + ORDER_ID + " INTEGER PRIMARY KEY,"
                + ORDER_ADDRESS + " TEXT,"
                + ORDER_BUYER + " INTEGER,"
                + ORDER_DATE + " DATE,"
                + ORDER_STATUS + " TINYINT,"
                + ORDER_SELLER + " INTEGER,"
                + ORDER_BOOK + " INTEGER,"
                + " FOREIGN KEY ("+BOOK_SELLER+") REFERENCES "+TABLE1_NAME+"("+T1COL1+"),"
                + " FOREIGN KEY ("+ORDER_BUYER+") REFERENCES "+TABLE1_NAME+"("+T1COL1+"),"
                + " FOREIGN KEY ("+ORDER_BOOK+") REFERENCES "+TABLE_BOOK+"("+BOOK_ID+"));";
        sqLiteDatabase.execSQL(query);

        query = "Insert into " + TABLE1_NAME
                + "("
                + T1COL2 + ","
                + T1COL3 + ","
                + T1COL4 + ","
                + T1COL5 + ") Values (" +
                "'admin',12345,'admin@gmail.com','Douglas College')," +
                "('joe',12345,'joe@gmail.com','Somewhere')";

        sqLiteDatabase.execSQL(query);



        query = "Insert into " + TABLE_BOOK
                + "("
                + BOOK_AUTHOR + ","
                + BOOK_DESC + ","
                + BOOK_SELLER + ","
                + BOOK_PRICE + ","
                + BOOK_TITLE + ","
                + BOOK_FOR_SALE + ","
                + BOOK_IMAGE +
                ") Values (" +
                "'Tony Faggioli','As our story unfolds, it becomes clear that there is hell, and then there is hell on earth.',1,18.99,'A Million to One: Beyond'," +
                "1, 'bookcover2'),('Jennifer L. Armentrout','Samantha is a stranger in her own life.',1,8.99,'Don''t Look Back',1, 'bookcover1')";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
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
        values.put(BOOK_PRICE, String.format("%.3f", price));
        values.put(BOOK_TITLE, title);
        values.put(BOOK_FOR_SALE, isForSale);
        Random rand = new Random();
        int upperbound = 5;
        int int_random = rand.nextInt(upperbound) + 1;

        values.put(BOOK_IMAGE, "bookcover" + int_random);
        long l = sqLiteDatabase.insert(TABLE_BOOK,null, values);
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

    public Cursor getOrders(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDER;
        return sqLiteDatabase.rawQuery(query,null);
    }

    public Cursor getOrderBySellerId(int sellerId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE SellerId='" + sellerId + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getOrderByBuyerId(int buyerId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDER + " WHERE BuyerId='" + buyerId + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public boolean insertOrder(int bookId,int buyerId,String address, int sellerId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_ADDRESS, address);
        values.put(ORDER_BUYER, buyerId);
        values.put(ORDER_SELLER, sellerId);
        values.put(ORDER_STATUS, false);
        values.put(ORDER_BOOK, bookId);
        values.put(ORDER_DATE, new SimpleDateFormat("YYYY-MM-dd").format(Calendar.getInstance().getTime()));

        long l = sqLiteDatabase.insert(TABLE_ORDER,null, values);
        return l > 0;
    }

    public boolean markAsDelivered(int orderId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_STATUS, true);
        int u = sqLiteDatabase.update(TABLE_ORDER,values,"id=?",
                new String[]{Integer.toString(orderId)});

        return u > 0;
    }
}
