package com.example.where2eatkotlinapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RestaurantViewModel : ViewModel() {
    var _nameFilter = MutableLiveData<String>()
    var _priceFilter = MutableLiveData<String>()
    var _currentLat = MutableLiveData<String>()
    var _currentLong = MutableLiveData<String>()
    var _distanceFilter = MutableLiveData<String>()

    val nameFilter get() = _nameFilter
    val priceFilter get() = _priceFilter
    val currentLat get() = _currentLat
    val currentLong get() = _currentLong
    val distanceFilter get() = _distanceFilter

}