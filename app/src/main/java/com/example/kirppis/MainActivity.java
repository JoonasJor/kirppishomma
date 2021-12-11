package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference db;
    private final ArrayList<CustomView> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        //kirjautuminen
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Log.d("debuggi", "not signed in");
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
        else{
            Log.d("debuggi", "signed in " + currentUser);
            loadItems();
        }
    }

    //tuotelistan lataus firebasesta
    private void loadItems() {
        db.child("items").addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("debuggi" ,"item count: " + snapshot.getChildrenCount());

                //ladataan ensin databasesta tuotteet itemListiin
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    itemList.add(new CustomView(postSnapshot.getValue(Item.class).getImage(),
                                                postSnapshot.getValue(Item.class).getName(),
                                      postSnapshot.getValue(Item.class).getPrice() + "€"));
                }
                // Now create the instance of the CustomArrayAdapter and pass
                // the context and arrayList created above
                CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(MainActivity.this, itemList);

                // create the instance of the ListView to set the CustomArrayAdapter
                ListView listView = findViewById(R.id.listView);

                // set the CustomArrayAdapter for ListView
                listView.setAdapter(customArrayAdapter);

                //viedään tuotteen id ItemListingiin klikattaessa sitä
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        goToItemListing(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: " ,error.toString());
            }
        }));
    }

    public void goToShoppingCart(View view){
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);
    }

    public void goToItemListing(int i){
        Intent intent = new Intent(this, ItemListing.class);
        intent.putExtra("item id", String.valueOf(i));
        startActivity(intent);
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}