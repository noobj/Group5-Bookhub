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

public class BuyBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);

        String[] books = {"Book1", "Book2", "Book3", "Book4", "Book5", "Book6", "Book7", "Book8", "Book9", "Book10"};
        ListView ls = findViewById(R.id.lsBuyBook);

        ArrayList<ImageAndText> objList = new ArrayList<>();
        objList.add(new ImageAndText("Book1", R.drawable.bookcover1));
        objList.add(new ImageAndText("Book2", R.drawable.bookcover2));
        objList.add(new ImageAndText("Book3", R.drawable.bookcover3));
        objList.add(new ImageAndText("Book4", R.drawable.bookcover4));
        objList.add(new ImageAndText("Book5", R.drawable.bookcover5));
        objList.add(new ImageAndText("Book6", R.drawable.bookcover1));
        objList.add(new ImageAndText("Book7", R.drawable.bookcover2));
        objList.add(new ImageAndText("Book8", R.drawable.bookcover3));
        objList.add(new ImageAndText("Book9", R.drawable.bookcover4));
        objList.add(new ImageAndText("Book10", R.drawable.bookcover5));

        ListAdapter adapter = new CustomAdapterBuy(this, objList);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
                        break;
                }
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
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(BuyBookActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(BuyBookActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(BuyBookActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });

    }
}