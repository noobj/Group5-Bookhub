package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView lsBought;
    ListView lsSold;
    ArrayList<String> bookTitlesBought;
    ArrayList<Integer> bookImagesBought;
    ArrayList<String> bookPricesBought;
    ArrayList<Integer> orderIdsBought;
    ArrayList<String> bookTitlesSold;
    ArrayList<Integer> bookImagesSold;
    ArrayList<String> bookPricesSold;
    ArrayList<Integer> orderIdsSold;

    CustomAdapterBought adapterBought;
    CustomAdapterSold adapterSold;
    ArrayList<ImageAndText> objListBought;
    ArrayList<ImageAndText> objListSold;

    SharedPreferences sharedPreferences;

    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        databaseHelper = new DatabaseHelper(this);
        lsBought = findViewById(R.id.lsBought);
        lsSold = findViewById(R.id.lsSold);

        //Retrieve userId from SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        //set default value when userId not found
        userId = sharedPreferences.getInt("userId", -1);
        
        fetchBoughtOrders();
        adapterBought = new CustomAdapterBought(this, objListBought);
        lsBought.setAdapter(adapterBought);
        lsBought.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderHistoryActivity.this, OrderHistoryDetailsActivity.class);
                intent.putExtra("ORDER_ID", orderIdsBought.get(position));
                intent.putExtra("BOOK_TITLE", bookTitlesBought.get(position));
                intent.putExtra("BOOK_IMAGE", bookImagesBought.get(position));
                intent.putExtra("BOOK_PRICE", bookPricesBought.get(position));
                startActivity(intent);
            }
        });


        fetchSoldOrders();
        adapterSold = new CustomAdapterSold(this, objListSold);
        lsSold.setAdapter(adapterSold);
        lsSold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderHistoryActivity.this, OrderHistoryDetailsActivity.class);
                intent.putExtra("ORDER_ID", orderIdsSold.get(position));
                intent.putExtra("BOOK_TITLE", bookTitlesSold.get(position));
                intent.putExtra("BOOK_IMAGE", bookImagesSold.get(position));
                intent.putExtra("BOOK_PRICE", bookPricesSold.get(position));
                startActivity(intent);
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.order);

//         Perform item reselected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedId = menuItem.getItemId();

                if (selectedId == R.id.buy) {
                    // Handle "Buy" selection
                    startActivity(new Intent(OrderHistoryActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(OrderHistoryActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(OrderHistoryActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }

    private void fetchBoughtOrders(){
        objListBought = new ArrayList<>();
        orderIdsBought = new ArrayList<>();
        bookTitlesBought = new ArrayList<>();
        bookImagesBought = new ArrayList<>();
        bookPricesBought = new ArrayList<>();
        Cursor cursor = databaseHelper.getOrderByBuyerId(userId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int orderIdIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_ID);
                int orderId = cursor.getInt(orderIdIndex);
                orderIdsBought.add(orderId);
                int bookIdIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_BOOK);
                int bookId = cursor.getInt(bookIdIndex);
                Cursor bookCursor = databaseHelper.getBookById(bookId);
                if (bookCursor != null && bookCursor.moveToFirst()) {
                    int titleIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_TITLE);
                    String title = bookCursor.getString(titleIndex);
                    bookTitlesBought.add(title);
                    int imageIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_IMAGE);
                    String imageName = bookCursor.getString(imageIndex);
                    int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    bookImagesBought.add(imageResource);
                    int statusIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_STATUS);
                    int statusValue = cursor.getInt(statusIndex);
                    String status;
                    if (statusValue == 1)
                        status = "Delivered";
                    else status = "In Transit";
                    objListBought.add(new ImageAndText(title, imageResource, status));
                    int priceIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_PRICE);
                    String price = bookCursor.getString(priceIndex);
                    bookPricesBought.add(price);
                }
                if (bookCursor != null) {
                    bookCursor.close();
                }
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void fetchSoldOrders(){
        objListSold = new ArrayList<>();
        orderIdsSold = new ArrayList<>();
        bookTitlesSold = new ArrayList<>();
        bookImagesSold = new ArrayList<>();
        bookPricesSold = new ArrayList<>();
        Cursor cursor = databaseHelper.getOrderBySellerId(userId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int orderIdIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_ID);
                int orderId = cursor.getInt(orderIdIndex);
                orderIdsSold.add(orderId);
                int bookIdIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_BOOK);
                int bookId = cursor.getInt(bookIdIndex);
                Cursor bookCursor = databaseHelper.getBookById(bookId);
                if (bookCursor != null && bookCursor.moveToFirst()) {
                    int titleIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_TITLE);
                    String title = bookCursor.getString(titleIndex);
                    bookTitlesSold.add(title);
                    int imageIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_IMAGE);
                    String imageName = bookCursor.getString(imageIndex);
                    int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    bookImagesSold.add(imageResource);
                    int statusIndex = cursor.getColumnIndex(DatabaseHelper.ORDER_STATUS);
                    int statusValue = cursor.getInt(statusIndex);
                    String status;
                    if (statusValue == 1)
                        status = "Delivered";
                    else status = "In Transit";
                    objListSold.add(new ImageAndText(title, imageResource, status));
                    int priceIndex = bookCursor.getColumnIndex(DatabaseHelper.BOOK_PRICE);
                    String price = bookCursor.getString(priceIndex);
                    bookPricesSold.add(price);
                }
                if (bookCursor != null) {
                    bookCursor.close();
                }
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }
}