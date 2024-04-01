package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class OrderHistoryDetailsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        TextView orderBookName = findViewById(R.id.tvOrdHisBookName);
        TextView orderStatus = findViewById(R.id.tvOrHiStatus);
        TextView orderAddress = findViewById(R.id.tvOrHiAddress);
        TextView orderDate = findViewById(R.id.tvOrHiDate);
        TextView orderPrice = findViewById(R.id.tvOrHiPrice);
        Button editButton = findViewById(R.id.btnEditOrdHis);
        ImageView orderBookImg = findViewById(R.id.imgVbook);

        Intent intent = getIntent();

        if (intent != null) {
            int orderId = intent.getIntExtra("ORDER_ID", -1);
            String bookTitle = intent.getStringExtra("BOOK_TITLE");
            int bookImage = intent.getIntExtra("BOOK_IMAGE", -1);
            String bookPrice = intent.getStringExtra("BOOK_PRICE");

            //Retrieve order details from db
            databaseHelper = new DatabaseHelper(this);
            Cursor cursor = databaseHelper.getOrderById(orderId);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int index = cursor.getColumnIndex(DatabaseHelper.ORDER_STATUS);
                    int status = cursor.getInt(index);
                    index = cursor.getColumnIndex(DatabaseHelper.ORDER_ADDRESS);
                    String address = cursor.getString(index);
                    index = cursor.getColumnIndex(DatabaseHelper.ORDER_DATE);
                    String oDate = cursor.getString(index);
                    index = cursor.getColumnIndex(DatabaseHelper.ORDER_BUYER);
                    int buyerId = cursor.getInt(index);
                    index = cursor.getColumnIndex(DatabaseHelper.ORDER_SELLER);
                    int sellerId = cursor.getInt(index);

                    //set status as in transit or delivered
                    String statusValue;
                    if (status == 1){
                        statusValue = "Delivered";
                        editButton.setVisibility(View.INVISIBLE);
                    } else {
                        statusValue = "In Transit";
                    }

                    //Set retrieved book details and order details to view
                    orderBookName.setText(bookTitle);
                    orderStatus.setText(statusValue);
                    orderAddress.setText(address);
                    orderDate.setText(oDate);
                    orderPrice.setText(bookPrice);
                    orderBookImg.setImageResource(bookImage);
                    orderBookImg.setTag(bookImage); // Set the tag as the resource ID

                    //Retrieve userId from SharedPreferences
                    sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    //set default value when userId not found
                    int userId = sharedPreferences.getInt("userId", -1);

                    editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Check if userId is equal to sellerId
                            if (userId == sellerId) {
                                //navigate to edit screen for seller
                                Intent intent = new Intent(OrderHistoryDetailsActivity.this, EditOrderDetailsSellerActivity.class);

                                //pass the order details
                                intent.putExtra("orderId",orderId);
                                intent.putExtra("bookName", orderBookName.getText().toString());
                                intent.putExtra("address", orderAddress.getText().toString());
                                // Pass the resource ID of the image if available
                                int resourceId = (int) orderBookImg.getTag();
                                intent.putExtra("bookImage", resourceId);

                                startActivity(intent);
                            }
                            //Check if userId is equal to buyerId
                            else if (userId == buyerId) {
                                Intent intent = new Intent(OrderHistoryDetailsActivity.this, EditOrderDetailsBuyerActivity.class);

                                //pass the order details
                                intent.putExtra("orderId",orderId);
                                intent.putExtra("bookName", orderBookName.getText().toString());
                                intent.putExtra("address", orderAddress.getText().toString());
                                // Pass the resource ID of the image if available
                                int resourceId = (int) orderBookImg.getTag();
                                intent.putExtra("bookImage", resourceId);

                                startActivity(intent);
                            }
                        }
                    });
                    cursor.close();
                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        /* editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderHistoryDetailsActivity.this,EditOrderDetailsBuyerActivity.class));
            }
        });*/

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