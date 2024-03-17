package com.example.group5bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditOrderDetailsBuyerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_details_buyer);

        TextView tvBookName = findViewById(R.id.tvOrdHisBkName);
        Button btnUpdate = findViewById(R.id.btnUpdateAddr);

        tvBookName.setText("Don't Look Back");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditOrderDetailsBuyerActivity.this,EditOrderDetailsSellerActivity.class));
            }
        });

    }
}