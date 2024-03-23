package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SellBookActivity extends AppCompatActivity {

    EditText titleBook;
    EditText description;
    EditText price;
    EditText authorBook;
    RadioButton sellBook;
    RadioButton shareBook;
    Button addBtn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);

        titleBook = findViewById(R.id.edTitle);
        description = findViewById(R.id.edDescription);
        price = findViewById(R.id.edPrice);
        authorBook = findViewById(R.id.edAuthor);
        sellBook = findViewById(R.id.rdForSale);
        shareBook = findViewById(R.id.rdShare);
        addBtn = findViewById(R.id.btnAddBook);
        databaseHelper = new DatabaseHelper(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleBook.getText().toString().trim();
                String des = description.getText().toString().trim();
                String author = authorBook.getText().toString().trim();
                String priceStr = price.getText().toString().trim();
                boolean isForSale = sellBook.isChecked();

                //check for empty fields
                if(title.isEmpty() || des.isEmpty() || author.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(SellBookActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                float price = Float.parseFloat(priceStr);
                int sellerId = 1;

                boolean isInserted = databaseHelper.insertBook(title, author, des, price, isForSale, sellerId);

                if (isInserted) {
                    Toast.makeText(SellBookActivity.this, "Book added successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SellBookActivity.this, "Failed to add book", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.sell);

//         Perform item reselected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedId = menuItem.getItemId();

                if (selectedId == R.id.buy) {
                    // Handle "Buy" selection
                    startActivity(new Intent(SellBookActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(SellBookActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(SellBookActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}