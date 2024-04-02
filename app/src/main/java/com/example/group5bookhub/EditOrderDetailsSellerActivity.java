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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EditOrderDetailsSellerActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    int orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_details_seller);

        TextView bookName = findViewById(R.id.tvOrdHisBName);
        TextView orderAddress = findViewById(R.id.tvEditOrdSellAddress);
        ImageView imageViewSeller = findViewById(R.id.imgVbookSeller);
        Button btn = findViewById(R.id.btnMark);

        //Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        //get order details from Intent extras
        Intent intent =  getIntent();
        if(intent != null){
            orderId = intent.getIntExtra("orderId", -1);
            String bName = intent.getStringExtra("bookName");
            String address = intent.getStringExtra("address");
            int resourceId = getIntent().getIntExtra("bookImage", 0);

            //Set order details in EditText fields
            bookName.setText(bName);
            orderAddress.setText(address);
            if (resourceId != 0) {
                imageViewSeller.setImageResource(resourceId);
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update order status in the db
                boolean isUpdated = databaseHelper.markAsDelivered(orderId);
                if(isUpdated){
                    Toast.makeText(EditOrderDetailsSellerActivity.this, "Status Updated", Toast.LENGTH_LONG).show();
                    //redirect to Order history view with updated data
                    Intent intent = new Intent(EditOrderDetailsSellerActivity.this, OrderHistoryActivity.class);

                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(EditOrderDetailsSellerActivity.this, "Failed to Update", Toast.LENGTH_LONG).show();
                }

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
                    startActivity(new Intent(EditOrderDetailsSellerActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(EditOrderDetailsSellerActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(EditOrderDetailsSellerActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(EditOrderDetailsSellerActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}