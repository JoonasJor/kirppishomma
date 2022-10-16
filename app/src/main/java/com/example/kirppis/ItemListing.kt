package com.example.kirppis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ItemListing : AppCompatActivity() {
    private var db: DatabaseReference? = null
    private var imageView: ImageView? = null
    private var textDesc: TextView? = null
    private var textName: TextView? = null
    private var textPrice: TextView? = null
    private var textItemId: TextView? = null
    private var item: Item? = null
    private var itemId: String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_listing)
        textDesc = findViewById(R.id.textDescription)
        textName = findViewById(R.id.textName)
        textPrice = findViewById(R.id.textPrice)
        textItemId = findViewById(R.id.textItemId)
        imageView = findViewById(R.id.imageItem)
        val intent = intent
        itemId = intent.getStringExtra("item id")
        with(textItemId) {
            itemId = intent.getStringExtra("item id")
            this?.setText(itemId)
        }
        Log.d("debuggi", "item id: $itemId")
        db = FirebaseDatabase.getInstance().reference


        /*testi
        String testItemId = "6";
        addNewItem(testItemId, "nahkahanskat", "165", "tämäkin on komia", "ee");
        testi*/


        //ladataan tuote databasesta id perusteella
        db!!.child("items").child(itemId!!).get().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("debuggi", "Error getting data", task.exception)
            } else {
                //muunnetaan(Type Cast) DataSnapshot tyyppinen tulos databasesta Item tyyppiseksi
                item = task.result.getValue(Item::class.java)
                assert(item != null)
                with(textDesc) { this?.setText(item!!.description) }
                with(textName) { this?.setText(item!!.name) }
                with(textPrice) { this?.setText(item!!.price + "€") }
                Picasso.get().load(item!!.image).into(imageView)
                Log.d("debuggi", task.result.value.toString())
            }
        }
    }

    fun addNewItem(
        itemId: String?,
        name: String?,
        price: String?,
        description: String?,
        image: String?
    ) {
        val item = Item(name, price, description, image)
        db!!.child("items").child(itemId!!).setValue(item)
    }

    fun addToShoppingCart(view: View?) {
        val intent = Intent(this, ShoppingCart::class.java)
        intent.putExtra("item id", itemId.toString())
        startActivity(intent)
    }
}