package com.example.group5_bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signUpText = findViewById(R.id.tvLoginSignUp);

        // Sign up click
        signUpText.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}