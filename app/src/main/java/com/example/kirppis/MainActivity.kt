package com.example.kirppis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private var db: DatabaseReference? = null
    private val itemList = ArrayList<CustomView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        //kirjautuminen
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d("debuggi", "not signed in")
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        } else {
            Log.d("debuggi", "signed in $currentUser")
            loadItems()
        }
    }

    //tuotelistan lataus firebasesta
    private fun loadItems() {
        db!!.child("items").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("debuggi", "item count: " + snapshot.childrenCount)

                //ladataan ensin databasesta tuotteet itemListiin
                for (postSnapshot in snapshot.children) {
                    itemList.add(
                        CustomView(
                            postSnapshot.getValue(Item::class.java)!!.image,
                            postSnapshot.getValue(Item::class.java)!!.name,
                            postSnapshot.getValue(Item::class.java)!!.price + "€"
                        )
                    )
                }
                // Now create the instance of the CustomArrayAdapter and pass
                // the context and arrayList created above
                val customArrayAdapter = CustomArrayAdapter(this@MainActivity, itemList)

                // create the instance of the ListView to set the CustomArrayAdapter
                val listView = findViewById<ListView>(R.id.listView)

                // set the CustomArrayAdapter for ListView
                listView.adapter = customArrayAdapter

                //viedään tuotteen id ItemListingiin klikattaessa sitä
                listView.onItemClickListener =
                    OnItemClickListener { adapterView, view, i, l -> goToItemListing(i) }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("The read failed: ", error.toString())
            }
        })
    }

    fun goToShoppingCart(view: View?) {
        val intent = Intent(this, ShoppingCart::class.java)
        startActivity(intent)
    }

    fun goToItemListing(i: Int) {
        val intent = Intent(this, ItemListing::class.java)
        intent.putExtra("item id", i.toString())
        startActivity(intent)
    }

    fun signOut(view: View?) {
        AuthUI.getInstance().signOut(this)
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}