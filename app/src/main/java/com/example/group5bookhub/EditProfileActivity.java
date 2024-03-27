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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EditProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText edTxtUserName = findViewById(R.id.edTxtUserName);
        EditText edTxtUserAddress = findViewById(R.id.edTxtUserAddress);
        EditText edTxtUserEmail = findViewById(R.id.edTxtUserEmail);
        Button btnUpdate = findViewById(R.id.btnUserUpdate);

        //Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        //get the user ID of the logged-in user from SharedPreferences
        int userId = sharedPreferences.getInt("userId", -1);

        //get user details from Intent extras
        Intent intent =  getIntent();
        if(intent != null){
            String userName = intent.getStringExtra("userName");
            String userAddress = intent.getStringExtra("userAddress");
            String userEmail = intent.getStringExtra("userEmail");

            //Set user details in EditText fields
            edTxtUserName.setText(userName);
            edTxtUserAddress.setText(userAddress);
            edTxtUserEmail.setText(userEmail);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the new values entered by the user
                String newUserName = edTxtUserName.getText().toString().trim();
                String newUserAddress = edTxtUserAddress.getText().toString().trim();
                String newUserEmail = edTxtUserEmail.getText().toString().trim();
                
                //check if the user ID is valid
                if (userId != -1) {
                    //update the user details in the db
                    boolean isUpdated = databaseHelper.updateUserDetails(userId, newUserName, newUserAddress, newUserEmail);
                    if(isUpdated){
                        Toast.makeText(EditProfileActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                        //redirect to UserProfileActivity with updated data
                        Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                        intent.putExtra("userName", newUserName);
                        intent.putExtra("userAddress", newUserAddress);
                        intent.putExtra("userEmail", newUserEmail);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(EditProfileActivity.this, "Failed to Update", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditProfileActivity.this, "User ID not found", Toast.LENGTH_LONG).show();
                }
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
                    startActivity(new Intent(EditProfileActivity.this, BookDetailsActivity.class));
                } else if (selectedId == R.id.sell) {
                    // Handle "Sell" selection
                    startActivity(new Intent(EditProfileActivity.this, SellBookActivity.class));
                } else if (selectedId == R.id.order) {
                    // Handle "Order" selection
                    startActivity(new Intent(EditProfileActivity.this, OrderHistoryActivity.class));
                } else if (selectedId == R.id.profile) {
                    // Handle profile selection
                    startActivity(new Intent(EditProfileActivity.this, UserProfileActivity.class));
                }
                return true;
            }
        });
    }
}