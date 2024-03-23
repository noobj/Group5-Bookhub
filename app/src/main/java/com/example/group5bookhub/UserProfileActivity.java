package com.example.group5bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView userName = findViewById(R.id.tvUserName);
        TextView userAddress = findViewById(R.id.tvUaddress);
        TextView userPhone = findViewById(R.id.tvUphone);
        TextView userEmail = findViewById(R.id.tvUemail);
        Button editProfile = findViewById(R.id.btnEditProfile);
        Button logout = findViewById(R.id.btnLogout);

        userName.setText("Gayali Methmini");
        userAddress.setText("19022 119B Ave New West");
        userPhone.setText("6048489812");
        userEmail.setText("gayali.9812@gmail.com");

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,EditProfileActivity.class));
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