package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class ItemListing extends AppCompatActivity {

    private DatabaseReference db;
    private ImageView imageView;
    private TextView textDesc, textName, textPrice, textItemId;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);

        textDesc = findViewById(R.id.textDescription);
        textName = findViewById(R.id.textName);
        textPrice = findViewById(R.id.textPrice);
        textItemId = findViewById(R.id.textItemId);
        imageView = findViewById(R.id.imageItem);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("item id");
        textItemId.setText(itemId);
        Log.d("debuggi", "item id: " + itemId);

        db = FirebaseDatabase.getInstance().getReference();


        /*testi
        String testItemId = "6";
        addNewItem(testItemId, "nahkahanskat", "165", "tämäkin on komia", "ee");
        testi*/


        //ladataan tuote databasesta id perusteella
        db.child("items").child(itemId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("debuggi", "Error getting data", task.getException());
                }
                else {
                    //muunnetaan(Type Cast) DataSnapshot tyyppinen tulos databasesta Item tyyppiseksi
                    item = (Item) task.getResult().getValue(Item.class);

                    //sijoitetaan arvot näkymään
                    textDesc.setText(item.description);
                    textName.setText(item.name);
                    textPrice.setText(item.price + "€");
                    Picasso.get().load(item.image).into(imageView);

                    Log.d("debuggi", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    public void addNewItem(String itemId, String name, String price, String description, String image) {
        Item item = new Item(name, price, description, image);

        db.child("items").child(itemId).setValue(item);
    }




}

