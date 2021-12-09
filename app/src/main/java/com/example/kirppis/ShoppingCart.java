package com.example.kirppis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    private ArrayList<String> b = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("item id");

        b.add(itemId);
        Log.d("debuggi", String.valueOf(b));

    }
}