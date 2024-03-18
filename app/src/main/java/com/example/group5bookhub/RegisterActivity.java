package com.example.group5bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textSignUp = findViewById(R.id.tvSignUp);
        Button btnSignUp = findViewById(R.id.btnRegister);
        EditText username = findViewById(R.id.edTextName);
        EditText address = findViewById(R.id.edTextAddress);
        EditText email = findViewById(R.id.editTextEmailAddress);
        EditText password = findViewById(R.id.editTextPassword);
        EditText confirmPassword = findViewById(R.id.editTextPasswordReEnter);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                // Check if any field is empty
                if (name.isEmpty() || addr.isEmpty() || mail.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!pass.equals(confirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert the user into the database
                boolean inserted = databaseHelper.insertUser(name, pass, mail, addr);
                if (inserted) {
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    // Redirect to BuyBookActivity
                    startActivity(new Intent(RegisterActivity.this, BuyBookActivity.class));
                    finish(); // Close this activity
                } else {
                    Toast.makeText(RegisterActivity.this, "Error registering user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textSignUp.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}