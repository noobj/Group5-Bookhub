package com.example.group5bookhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditOrderDetailsSellerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_details_seller);

        TextView bookName = findViewById(R.id.tvOrdHisBName);
        TextView address = findViewById(R.id.tvEditOrdSellAddress);

        bookName.setText("Don't Look Back");
        address.setText("28055 150 Blakely Avenue New Westminister");
        Button btn = findViewById(R.id.btnMark);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditOrderDetailsSellerActivity.this,LoginActivity.class));
            }
        });
    }
}