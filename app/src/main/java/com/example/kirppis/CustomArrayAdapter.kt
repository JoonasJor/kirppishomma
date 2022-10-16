package com.example.kirppis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CustomArrayAdapter  // invoke the suitable constructor of the ArrayAdapter class
    (context: Context, arrayList: ArrayList<CustomView>?) :
    ArrayAdapter<CustomView?>(context, 0, arrayList!! as List<CustomView?>) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView =
                LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false)
        }

        // get the position of the view from the ArrayAdapter
        val currentNumberPosition = getItem(position)

        // then according to the position of the view assign the desired image for the same
        val imageView = currentItemView!!.findViewById<ImageView>(R.id.imageView)
        assert(currentNumberPosition != null)
        if (currentNumberPosition != null) {
            Picasso.get().load(currentNumberPosition.imageUrl).into(imageView)
        }

        // then according to the position of the view assign the desired TextView 1 for the same
        val textView1 = currentItemView.findViewById<TextView>(R.id.textView1)
        if (currentNumberPosition != null) {
            textView1.text = currentNumberPosition.itemName
        }

        // then according to the position of the view assign the desired TextView 2 for the same
        val textView2 = currentItemView.findViewById<TextView>(R.id.textView2)
        if (currentNumberPosition != null) {
            textView2.text = currentNumberPosition.itemPrice
        }

        // then return the recyclable view
        return currentItemView
    }
}