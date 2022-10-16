package com.example.kirppis

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Item {
    var name: String? = null
        private set
    var price: String? = null
        private set
    var description: String? = null
        private set
    var image: String? = null
        private set

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(dbName: String?, dbPrice: String?, dbDescription: String?, dbImage: String?) {
        name = dbName
        price = dbPrice
        description = dbDescription
        image = dbImage
    }
}