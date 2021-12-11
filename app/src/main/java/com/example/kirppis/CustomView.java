package com.example.kirppis;

    public class CustomView {

        //url ImageViewiin
        private final String imageUrl;
        //TextView 1
        private final String itemName;
        //TextView 2
        private final String itemPrice;

        // create constructor to set the values for all the parameters of the each single view
        public CustomView(String dbImageUrl, String dbItemName, String dbItemPrice) {
            imageUrl = dbImageUrl;
            itemName = dbItemName;
            itemPrice = dbItemPrice;
        }

        // getter method for returning the ID of the imageview
        public String getImageUrl() {
            return imageUrl;
        }

        // getter method for returning the ID of the TextView 1
        public String getItemName() {
            return itemName;
        }

        // getter method for returning the ID of the TextView 2
        public String getItemPrice() {
            return itemPrice;
        }
}
