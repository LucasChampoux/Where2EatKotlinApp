package com.example.where2eatkotlinapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class RestaurantAdapter : ListAdapter<Restaurant, RestaurantViewHolder>(RestaurantDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_row, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}