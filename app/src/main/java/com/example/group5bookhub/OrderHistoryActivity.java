package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        String[] booksBought = {"Book1", "Book2", "Book3", "Book4", "Book5"};
        String[] booksSold = {"Book6", "Book7", "Book8", "Book9", "Book10"};
        String[] status = {"Delivered", "In Transit"};

        ListView lsBought = findViewById(R.id.lsBought);
        ListView lsSold = findViewById(R.id.lsSold);

        ArrayList<ImageAndText> listBought = new ArrayList<>();
        listBought.add(new ImageAndText("Book1", R.drawable.bookcover1, "Delivered"));
        listBought.add(new ImageAndText("Book2", R.drawable.bookcover2, "In Transit"));
        listBought.add(new ImageAndText("Book3", R.drawable.bookcover3, "Delivered"));
        listBought.add(new ImageAndText("Book4", R.drawable.bookcover1, "Delivered"));
        listBought.add(new ImageAndText("Book5", R.drawable.bookcover2, "In Transit"));

        ListAdapter adapterBought = new CustomAdapterBought(this, listBought);
        lsBought.setAdapter(adapterBought);
        lsBought.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });

        ArrayList<ImageAndText> listSold = new ArrayList<>();
        listSold.add(new ImageAndText("Book6", R.drawable.bookcover4));
        listSold.add(new ImageAndText("Book7", R.drawable.bookcover5));
        listSold.add(new ImageAndText("Book8", R.drawable.bookcover3));
        listSold.add(new ImageAndText("Book9", R.drawable.bookcover4));
        listSold.add(new ImageAndText("Book10", R.drawable.bookcover5));

        ListAdapter adapterSold = new CustomAdapterSold(this, listSold);
        lsSold.setAdapter(adapterSold);
        lsSold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
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
}