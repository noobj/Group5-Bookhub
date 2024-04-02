package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EditOrderDetailsBuyerActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;
    int orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_details_buyer);

        TextView tvBookName = findViewById(R.id.tvOrdHisBkName);
        EditText edTxtAddress = findViewById(R.id.edTxtEditAdd);
        ImageView orderBookBuyerImg = findViewById(R.id.imgVbookBuyr);
        Button btnUpdate = findViewById(R.id.btnUpdateAddr);

        //Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        //int userId = sharedPreferences.getInt("userId", -1);

        //get order details from Intent extras
        Intent intent =  getIntent();
        if(intent != null){
            orderId = intent.getIntExtra("orderId", -1);
            String bName = intent.getStringExtra("bookName");
            String address = intent.getStringExtra("address");
            int resourceId = getIntent().getIntExtra("bookImage", 0);

            //Set order details in EditText fields
            tvBookName.setText(bName);
            edTxtAddress.setText(address);
            if (resourceId != 0) {
                orderBookBuyerImg.setImageResource(resourceId);
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the new address entered
                String newAddress = edTxtAddress.getText().toString().trim();

                //check if the user ID is valid
                if (orderId != -1) {
                    //update order details in the db
                    boolean isUpdated = databaseHelper.updateOrderAddress(orderId,newAddress);
                    if(isUpdated){
                        Toast.makeText(EditOrderDetailsBuyerActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                        //redirect to Order history view with updated data
                        Intent intent = new Intent(EditOrderDetailsBuyerActivity.this, OrderHistoryActivity.class);
                        intent.putExtra("address", newAddress);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(EditOrderDetailsBuyerActivity.this, "Failed to Update", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditOrderDetailsBuyerActivity.this, "User ID not found", Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(EditOrderDetailsBuyerActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(EditOrderDetailsBuyerActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(EditOrderDetailsBuyerActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(EditOrderDetailsBuyerActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });

    }
}