package com.example.where2eatkotlinapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val photo: ImageView
    val name: TextView
    val rating: TextView
    val price: TextView

    init{
        photo = v.findViewById(R.id.photo)
        name = v.findViewById(R.id.name)
        rating = v.findViewById(R.id.rating)
        price = v.findViewById(R.id.price)
    }

    public fun bindTo(r: Restaurant){
        name.text = r.name
        rating.text = r.rating
        price.text = r.price
    }
}