package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SellBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);

//        EditText titile = findViewById(R.id.edTitle);
//        EditText des = findViewById(R.id.edDescription);
//        EditText price = findViewById(R.id.edPrice);
//        Spinner categ = findViewById(R.id.spinnerCategory);
//        RadioButton sell = findViewById(R.id.rdForSale);
//        RadioButton share = findViewById(R.id.rdShare);
//        Button uploadImg = findViewById(R.id.btnUploadImg);
//        Button addBook = findViewById(R.id.btnAddBook);

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
                    startActivity(new Intent(SellBookActivity.this, BookDetailsActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(SellBookActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(SellBookActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}