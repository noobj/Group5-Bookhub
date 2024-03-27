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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BookDetailsActivity extends AppCompatActivity {
    ImageView bookImg;
    TextView bookTitle;
    TextView bookPrice;
    TextView bookDesc;
    TextView bookAuthor;
    Button btnBuy;
    Button btnBack;
    int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        bookImg = findViewById(R.id.imageView3);
        bookTitle = findViewById(R.id.tvBookTitle);
        bookPrice = findViewById(R.id.tvPriceOutput);
        bookDesc = findViewById(R.id.tvDescOutput);
        bookAuthor = findViewById(R.id.tvAuthorOutput);
        btnBuy = findViewById(R.id.btnBuy);
        btnBack = findViewById(R.id.btnBack);

        //Receive data from intent
        Intent intent = getIntent();
        if (intent != null) {
            //Retrieve bookId from intent
            bookId = intent.getIntExtra("BOOK_ID", -1);

            //Retrieve book details from database using bookId
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Cursor cursor = databaseHelper.getBookById(bookId);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DatabaseHelper.BOOK_TITLE);
                String title = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_PRICE);
                String price = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_DESC);
                String desc = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_AUTHOR);
                String authorName = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_IMAGE);
                String image = cursor.getString(index);
                int imageResource = getResources().getIdentifier(image, "drawable", getPackageName());
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_SELLER);
                int sellerId = cursor.getInt(index);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_FOR_SALE);
                int isSale = cursor.getInt(index);

                //Set retrieved book details to views
                bookTitle.setText(title);
                bookPrice.setText(price);
                bookDesc.setText(desc);
                bookAuthor.setText(authorName);
                bookImg.setImageResource(imageResource);

                //Retrieve userId from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                //set default value when userId not found
                int userId = sharedPreferences.getInt("userId", -1);

                //Check if userId is equal to sellerId
                if(userId == sellerId) {
                    //If yes, hide the buy button
                    btnBuy.setVisibility(View.INVISIBLE);
                }

                //Check if book is for share
                if (isSale == 0) {
                    //If yes, hide the buy button
                    btnBuy.setVisibility(View.INVISIBLE);
                }
            }
            cursor.close();
        }


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsActivity.this, DeliveryDetailsActivity.class);
                intent.putExtra("BOOK_ID", bookId);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookDetailsActivity.this, BuyBookActivity.class));
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
                    startActivity(new Intent(BookDetailsActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(BookDetailsActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(BookDetailsActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(BookDetailsActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}