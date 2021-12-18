package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class ItemListing extends AppCompatActivity {

    private DatabaseReference db;
    private ImageView imageView;
    private TextView textDesc, textName, textPrice;
    private Button buttonAddToCart;
    private Item item;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);

        textDesc = findViewById(R.id.textDescription);
        textName = findViewById(R.id.textName);
        textPrice = findViewById(R.id.textPrice);
        imageView = findViewById(R.id.imageItem);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("item id");
        Log.d("debuggi", "item id: " + itemId);

        db = FirebaseDatabase.getInstance().getReference();


        /*testi
        String testItemId = "6";
        addNewItem(testItemId, "nahkahanskat", "165", "tämäkin on komia", "ee");
        testi*/


        // ladataan tuote databasesta id perusteella
        db.child("items").child(itemId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("debuggi", "Error getting data", task.getException());
                }
                else {
                    // muunnetaan DataSnapshot tyyppinen tulos databasesta Item tyyppiseksi
                    item = (Item) task.getResult().getValue(Item.class);

                    // sijoitetaan arvot näkymään
                    assert item != null;
                    textDesc.setText(item.getDescription());
                    textName.setText(item.getName());
                    textPrice.setText(item.getPrice() + "€ (sis. alv 24%)");
                    Picasso.get().load(item.getImage()).into(imageView);

                    Log.d("debuggi", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // tarkistetaan onko tuote jo ostoskorissa
        boolean alreadyInCart = ShoppingCartHelper.listId.stream().anyMatch(x -> x == Integer.parseInt(itemId));
        if(alreadyInCart){
            buttonAddToCart.setEnabled(false);
        }
    }

    public void addNewItem(String itemId, String name, String price, String description, String image) {
        Item item = new Item(name, price, description, image);

        db.child("items").child(itemId).setValue(item);
    }

    public void addToShoppingCart(View view){

            ShoppingCartHelper.listShoppingCart.add(new CustomView(item.getImage(),
                                                                   item.getName(),
                                                         item.getPrice() + "€"));
            ShoppingCartHelper.listId.add(Integer.parseInt(itemId));

            String parsedPrice = item.getPrice().replace(',', '.');
            ShoppingCartHelper.priceTotal += Float.parseFloat(parsedPrice);
            view.setEnabled(false);

            Toast.makeText(ItemListing.this, "Tuote lisätty ostoskoriin", Toast.LENGTH_SHORT).show();
    }
}

