package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class BuyBookActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);

        databaseHelper = new DatabaseHelper(this);
        ls = findViewById(R.id.lsBuyBook);

        //Fetch book titles from database
        ArrayList<String> bookTitles = new ArrayList<>();
        ArrayList<String> bookImages = new ArrayList<>();
        Cursor cursor = databaseHelper.getBooks();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index = cursor.getColumnIndex(DatabaseHelper.BOOK_TITLE);
                String title = cursor.getString(index);
                bookTitles.add(title);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_IMAGE);
                bookImages.add(cursor.getString(index));
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        // Create ArrayList of ImageAndText objects
        ArrayList<ImageAndText> objList = new ArrayList<>();
        // Create book cover
        int[] bookCovers = {R.drawable.bookcover1, R.drawable.bookcover2, R.drawable.bookcover3, R.drawable.bookcover4, R.drawable.bookcover5};

        // Populate objList with book titles and covers
        for (int i = 0; i < bookTitles.size(); i++) {
            int imageResource = getResources().getIdentifier(bookImages.get(i) , "drawable", getPackageName());
            objList.add(new ImageAndText(bookTitles.get(i), imageResource));
        }

        ListAdapter adapter = new CustomAdapterBuy(this, objList);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(BuyBookActivity.this, BookDetailsActivity.class));
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