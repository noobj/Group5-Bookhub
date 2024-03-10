package com.example.group5_bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textSignUp = findViewById(R.id.tvSignUp);

        textSignUp.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
    }
}