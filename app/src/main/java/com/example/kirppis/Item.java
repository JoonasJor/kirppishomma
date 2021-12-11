package com.example.kirppis;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {

    private String name;
    private String price;
    private String description;
    private String image;

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Item(String dbName, String dbPrice, String dbDescription, String dbImage) {
        name = dbName;
        price = dbPrice;
        description = dbDescription;
        image = dbImage;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
