package com.example.kirppis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ItemListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);

        TextView textItemId = findViewById(R.id.textViewItemId);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("item id");
        textItemId.setText(itemId);
        Log.d("item id", itemId);
        //asd
    }
}