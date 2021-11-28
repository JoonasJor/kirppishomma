package com.example.kirppis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Log.d("debuggi", "not signed in");
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
        else{
            Log.d("debuggi", "signed in " + currentUser);
        }
    }

    public void goToShoppingCart(View view){
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);
    }
    public void goToItemListing(View view){
        Intent intent = new Intent(this, ItemListing.class);
        intent.putExtra("item id", String.valueOf(view.getTag()));
        startActivity(intent);
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}