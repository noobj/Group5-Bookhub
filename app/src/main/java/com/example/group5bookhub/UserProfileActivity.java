package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView name = findViewById(R.id.tvUserName);
        TextView address = findViewById(R.id.tvUaddress);
        TextView email = findViewById(R.id.tvUemail);
        Button editProfile = findViewById(R.id.btnEditProfile);
        Button logout = findViewById(R.id.btnLogout);

        //Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        //get the user ID of the logged-in user from SharedPreferences
        int userId = sharedPreferences.getInt("userId", -1);

        //Check if a valid user ID is retrieved
        if (userId != -1) {
            //Get user details from the database using the user ID
            Cursor cursor = databaseHelper.getUserById(userId);

            //Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                //get user details from the cursor
                int index = cursor.getColumnIndex(DatabaseHelper.T1COL2);
                String userName = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.T1COL5);
                String userAddress = cursor.getString(index);
                index = cursor.getColumnIndex(DatabaseHelper.T1COL4);
                String userEmail = cursor.getString(index);

                //Display user details in the activity
                name.setText(userName);
                address.setText(userAddress);
                email.setText(userEmail);

                // Close the cursor after use
                cursor.close();
            } else {
                //where user details are not found
                Toast.makeText(this, "User details not found", Toast.LENGTH_LONG).show();
            }
        } else {
            //where user ID is not found in the SharedPreferences
            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show();
        }
        //edit profile button click
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass the user details to EditProfileActivity
                Intent intent = new Intent(UserProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("userName", name.getText().toString());
                intent.putExtra("userAddress", address.getText().toString());
                intent.putExtra("userEmail", email.getText().toString());
                startActivity(intent);
            }
        });

        // Logout clicked
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete user ID from SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.apply();

                // Redirect to LoginActivity
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                finish(); // Close this activity
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.profile);


//      // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selectedId = menuItem.getItemId();

                if (selectedId == R.id.buy) {
                    // Handle "Buy" selection
                    startActivity(new Intent(UserProfileActivity.this, BuyBookActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(UserProfileActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(UserProfileActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });

    }
}