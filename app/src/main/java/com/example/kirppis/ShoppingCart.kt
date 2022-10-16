package com.example.kirppis

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ShoppingCart : AppCompatActivity() {
    private val b = ArrayList<String?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        val intent = intent
        val itemId = intent.getStringExtra("item id")
        b.add(itemId)
        Log.d("debuggi", b.toString())
    }
}