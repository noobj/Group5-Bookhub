package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class BuyBookActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView ls;
//    ListAdapter adapter;
    ArrayList<String> bookTitles;
    ArrayList<String> bookImages;
    ArrayList<Integer> bookIds;
    CustomAdapterBuy customAdapter;
    ArrayList<ImageAndText> objList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);

        databaseHelper = new DatabaseHelper(this);
        ls = findViewById(R.id.lsBuyBook);
        SearchView searchView = findViewById(R.id.searchView);

        //Initialize Arrays to store book titles, images and IDs
        bookTitles = new ArrayList<>();
        bookImages = new ArrayList<>();
        bookIds = new ArrayList<>();

        // Fetch all book titles, images, and ids from the database
        fetchBooks();


        // Create ArrayList of ImageAndText objects
        objList = new ArrayList<>();

        // Populate objList with book titles and covers
        for (int i = 0; i < bookTitles.size(); i++) {
            int imageResource = getResources().getIdentifier(bookImages.get(i) , "drawable", getPackageName());
            objList.add(new ImageAndText(bookTitles.get(i), imageResource));
        }

        customAdapter = new CustomAdapterBuy(this, objList);
        ls.setAdapter(customAdapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuyBookActivity.this, BookDetailsActivity.class);
                intent.putExtra("BOOK_ID", bookIds.get(position));
                startActivity(intent);
            }
        });

        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        // Restore full list when search query is empty
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                customAdapter.restoreFullList();
                return false;
            }
        });



        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        // Perform item reselected listener
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

    private void fetchBooks(){
        Cursor cursor = databaseHelper.getBooks();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index = cursor.getColumnIndex(DatabaseHelper.BOOK_TITLE);
                String title = cursor.getString(index);
                bookTitles.add(title);
                index = cursor.getColumnIndex(DatabaseHelper.BOOK_IMAGE);
                bookImages.add(cursor.getString(index));
                int indexId = cursor.getColumnIndex(DatabaseHelper.BOOK_ID);
                int id = cursor.getInt(indexId);
                bookIds.add(id);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void filter(String query) {
        ArrayList<ImageAndText> filteredList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            for (ImageAndText item : objList) {
                if (item.getTxt().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        } else {
            filteredList.addAll(objList);
        }
        customAdapter.filterList(filteredList);
    }


}