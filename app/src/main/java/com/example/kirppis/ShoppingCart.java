package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class ShoppingCart extends AppCompatActivity {

    private DatabaseReference db;
    private TextView priceTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        priceTotal = findViewById(R.id.textViewPriceTotal);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("debuggi", String.valueOf(ShoppingCartHelper.listShoppingCart));

        // listataan ostoskorissa olevat tuotteet CustomArrayAdapteria käyttäen
        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(ShoppingCart.this, ShoppingCartHelper.listShoppingCart);
        ListView listView = findViewById(R.id.listViewShoppingCart);
        listView.setAdapter(customArrayAdapter);

        // viedään tuotteen id ItemListingiin klikattaessa sitä
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToItemListing(ShoppingCartHelper.listId.get(i));
            }
        });

        // päivitetään kokonaishinta
        String parsedTotal = String.valueOf(ShoppingCartHelper.priceTotal).replace('.', ',');
        priceTotal.setText("Yhteensä: " + parsedTotal + "0 € (sis. alv 24%)");
    }

    private void goToItemListing(int i){
        Intent intent = new Intent(this, ItemListing.class);
        intent.putExtra("item id", String.valueOf(i));
        startActivity(intent);
    }

}