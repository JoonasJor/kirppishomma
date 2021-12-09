package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ArrayList<Item> itemList = new ArrayList<Item>();
    //private ArrayList<String> itemList = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listView);

        //kirjautuminen
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Log.d("debuggi", "not signed in");
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
        else{
            Log.d("debuggi", "signed in " + currentUser);
            //loadItems();

            final ArrayList<NumbersView> arrayList = new ArrayList<NumbersView>();

            // add all the values from 1 to 15 to the arrayList
            // the items are of the type NumbersView
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "1", "One"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "2", "Two"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "3", "Three"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "4", "Four"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "5", "Five"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "6", "Six"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "7", "Seven"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "8", "Eight"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "9", "Nine"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "10", "Ten"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "11", "Eleven"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "12", "Twelve"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "13", "Thirteen"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "14", "Fourteen"));
            arrayList.add(new NumbersView(R.drawable.common_full_open_on_phone, "15", "Fifteen"));

            // Now create the instance of the NumebrsViewAdapter and pass
            // the context and arrayList created above
            NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(this, arrayList);

            // create the instance of the ListView to set the numbersViewAdapter
            ListView numbersListView = findViewById(R.id.listView);

            // set the numbersViewAdapter for ListView
            numbersListView.setAdapter(numbersArrayAdapter);
        }
    }



    //database
    private void loadItems() {
        db.child("items").addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("debuggi" ,"item count: " + snapshot.getChildrenCount());

                //ladataan ensin databasesta tuotteet ArrayListiin
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    itemList.add(postSnapshot.getValue(Item.class));
                    //itemList.add(postSnapshot.getValue(Item.class).name);
                }

                //sitten lisätään ArrayListin sisältö ListViewiin ArrayAdapterilla

                //tällä hetkellä listView listaa vain Item objektit string muodossa,
                //tää pitää muuttaa silleen että se listaa tuotteiden kuvat ja vaikkapa hinnan ja nimen
                Log.d("debuggi", String.valueOf(itemList));
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, itemList);
                listView.setAdapter(arrayAdapter);

                //viedään tuotteen id ItemListingiin klikattaessa sitä
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(MainActivity.this, "clicked item: " + i + " " + itemList.get(i).name, Toast.LENGTH_SHORT).show();
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