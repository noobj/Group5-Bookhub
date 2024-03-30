package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DeliveryDetailsActivity extends AppCompatActivity {

    EditText address;
    Button order;
    Button cancel;
    int bookId;
    int buyerId;
    int sellerId;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        Intent intent = getIntent();

        databaseHelper = new DatabaseHelper(this);
        address = findViewById(R.id.edTextAddress);
        order = findViewById(R.id.btnOrder);
        cancel = findViewById(R.id.btnCancel);
        bookId = intent.getIntExtra("BOOK_ID", -1);
        buyerId = intent.getIntExtra("BOOK_BUYER", -1);
        sellerId = intent.getIntExtra("BOOK_SELLER", -1);

//        //Retrieve userId from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//        //set default value when userId not found
//        buyerId = sharedPreferences.getInt("userId", -1);
//
//        //Receive data from intent
//        Intent intent = getIntent();
//        if (intent != null) {
//            //Retrieve bookId from intent
//            bookId = intent.getIntExtra("BOOK_ID", -1);
//
//            //Retrieve book details from database using bookId
//            DatabaseHelper databaseHelper = new DatabaseHelper(this);
//            Cursor cursor = databaseHelper.getBookById(bookId);
//            if (cursor != null && cursor.moveToFirst()) {
//                int index = cursor.getColumnIndex(DatabaseHelper.BOOK_SELLER);
//                sellerId = cursor.getInt(index);
//            }
//        }


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addr = address.getText().toString().trim();
                databaseHelper.insertOrder(bookId, buyerId, addr, sellerId);
                Toast.makeText(DeliveryDetailsActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DeliveryDetailsActivity.this, BuyBookActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

//         Perform item reselected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedId = menuItem.getItemId();

                if (selectedId == R.id.buy) {
                    // Handle "Buy" selection
//                    startActivity(new Intent(BookDetailsActivity.this, MainActivity.class));
                    startActivity(new Intent(DeliveryDetailsActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(DeliveryDetailsActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(DeliveryDetailsActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(DeliveryDetailsActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}