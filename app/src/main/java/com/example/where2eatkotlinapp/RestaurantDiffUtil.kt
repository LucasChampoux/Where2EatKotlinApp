package com.example.where2eatkotlinapp

import androidx.recyclerview.widget.DiffUtil

class RestaurantDiffUtil : DiffUtil.ItemCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant) =
        oldItem.name == newItem.name &&
                oldItem.price == newItem.price &&
                oldItem.rating == newItem.rating
}