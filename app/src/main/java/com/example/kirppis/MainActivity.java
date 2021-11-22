package com.example.kirppis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GoToShoppingCart(View view){
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);
    }
    public void GoToItemListing(View view){
        Intent intent = new Intent(this, ItemListing.class);
        intent.putExtra("item id", String.valueOf(view.getTag()));
        startActivity(intent);
    }
}