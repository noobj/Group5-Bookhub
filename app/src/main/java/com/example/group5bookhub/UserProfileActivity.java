package com.example.group5bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,LoginActivity.class));
            }
        });
    }
}