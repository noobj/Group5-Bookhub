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


        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted = databaseHelper.insertUser(username.getText().toString(),
                        password.getText().toString(),email.getText().toString(),
                        address.getText().toString());
                if(inserted){
                    Toast.makeText(RegisterActivity.this,"User is inserted ",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"User is not inserted ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        textSignUp.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}