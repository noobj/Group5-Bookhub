package com.example.group5bookhub;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edTextEmail, edTextPassword;
    Button btnSignIn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        edTextEmail = findViewById(R.id.edTextEmail);
        edTextPassword = findViewById(R.id.edTextPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        TextView signUpBtn = findViewById(R.id.tvLoginSignUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edTextEmail.getText().toString().trim();
                String password = edTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = dbHelper.getUserByEmail(email);

                if (cursor != null) {
                    int passwordIndex = cursor.getColumnIndex(DatabaseHelper.T1COL3);
                    if (passwordIndex != -1 && cursor.moveToFirst()) {
                        String dbPassword = cursor.getString(passwordIndex);

                        if (password.equals(dbPassword)) {
                            // Passwords match, login successful
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, BuyBookActivity.class));
                        } else {
                            // Passwords don't match
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // No user found with the entered email
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                } else {
                    // Handle null cursor
                    Toast.makeText(LoginActivity.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }
}
