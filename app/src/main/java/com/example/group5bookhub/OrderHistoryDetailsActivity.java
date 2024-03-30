package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class OrderHistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);
        Intent intent = getIntent();


        TextView orderBookName = findViewById(R.id.tvOrdHisBookName);
        TextView orderStatus = findViewById(R.id.tvOrHiStatus);
        TextView orderAddress = findViewById(R.id.tvOrHiAddress);
        TextView orderDate = findViewById(R.id.tvOrHiDate);
        TextView orderPrice = findViewById(R.id.tvOrHiPrice);
        Button editButton = findViewById(R.id.btnEditOrdHis);
        ImageView orderBookImg = findViewById(R.id.imgVbook);

        int orderId = intent.getIntExtra("ORDER_ID", -1);
        String bookTitle = intent.getStringExtra("BOOK_TITLE");
        int bookImage = intent.getIntExtra("BOOK_IMAGE", -1);
        String bookPrice = intent.getStringExtra("BOOK_PRICE");
        orderBookName.setText(bookTitle);
        orderPrice.setText(bookPrice);
        orderBookImg.setImageResource(bookImage);


//        orderBookName.setText("Don't Look Back");
//        orderStatus.setText("In Transit");
//        orderAddress.setText("28055 150 Blakely Avenue New Westminister");
//        orderDate.setText("29/01/2024");
//        orderPrice.setText("$40");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderHistoryDetailsActivity.this,EditOrderDetailsBuyerActivity.class));
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.order);


//      // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedId = menuItem.getItemId();

                if (selectedId == R.id.buy) {
                    // Handle "Buy" selection
                    startActivity(new Intent(OrderHistoryDetailsActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(OrderHistoryDetailsActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(OrderHistoryDetailsActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(OrderHistoryDetailsActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}