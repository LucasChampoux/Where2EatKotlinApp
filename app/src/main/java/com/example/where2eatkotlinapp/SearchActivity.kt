package com.example.where2eatkotlinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.where2eatkotlinapp.databinding.ActivitySearchBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class SearchActivity : AppCompatActivity() {

    lateinit var adapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val fab : FloatingActionButton = findViewById(R.id.fab)

        var priceFilter : String = ""
        var ratingFilter : String = ""
        var distanceFilter : String = ""
        var currentLong : String = ""
        var currentLat : String = ""
        var priceVal : String = ""
        var distVal : String = ""
        var restaurantViewList : MutableList<Restaurant> = mutableListOf()

        var startup: Restaurant = Restaurant("Startup", "n/a", "n/a")



        if(intent.hasExtra("priceFilter")){
            priceFilter = intent.getStringExtra("priceFilter").toString()
        }

        if(intent.hasExtra("ratingFilter")){
            ratingFilter = intent.getStringExtra("ratingFilter").toString()
        }

        if(intent.hasExtra("distanceFilter")){
            distanceFilter = intent.getStringExtra("distanceFilter").toString()
        }

        if(intent.hasExtra("currentLong")) {
            currentLong = intent.getStringExtra("currentLong").toString()
        }

        if(intent.hasExtra("currentLat")){
            currentLat = intent.getStringExtra("currentLat").toString()
        }

        when(priceFilter){
            "$" -> priceVal = "0"
            "$$" -> priceVal = "1"
            "$$$" -> priceVal = "2"
            "$$$$" -> priceVal = "3"
            "$$$$$" -> priceVal = "4"
        }

        when(distanceFilter){
            ".25 km" -> distVal = "250"
            ".5 km" -> distVal = "500"
            ".75 km" -> distVal = "750"
            "1 km" -> distVal = "1000"
            "1.25 km" -> distVal = "1250"
            "1.5 km" -> distVal = "1500"
        }

        val queue = Volley.newRequestQueue(this)

        //api call url
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?keyword=" +
                "&location=$currentLat%2C$currentLong" +
                "&radius=$distVal" +
                "&type=restaurant" +
                "&maxprice=$priceVal" +
                "&opennow=true" +
                "&key=[YOUR_API_KEY]" //TODO Fix having api key hardcoded

        adapter = RestaurantAdapter()
        val layoutMgr = LinearLayoutManager(this)

        val restaurantList : RecyclerView = findViewById(R.id.restaurant_list)
        restaurantList.adapter = adapter
        restaurantList.layoutManager = LinearLayoutManager(this)

        restaurantList.addItemDecoration(DividerItemDecoration(this, layoutMgr.orientation))

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                val status = response.getString("status")
                if( status != "OK"){
                    val tempRestaurant : Restaurant = Restaurant("No results", "Rating: n/a", "Price: na/a")
                    restaurantViewList.add(tempRestaurant)
                    adapter.submitList(restaurantViewList)
                }
                else {
                    var restaurantResult: JSONArray = response.getJSONArray("results")

                    for (i in 0 until restaurantResult.length() - 1) {
                        var currentObject: JSONObject = restaurantResult.getJSONObject(i)


                        var restaurant : Restaurant = Restaurant(currentObject.getString("name"), "Rating: "+currentObject.getString("rating") + " stars", "Price level: " + currentObject.getString("price_level"))

                        restaurantViewList.add(restaurant)
                    }
                    adapter.submitList(restaurantViewList)
                }
            },
            { error ->
                val tempRestaurant : Restaurant = Restaurant("Error Parsing", "n/a", "na/a")
                restaurantViewList.add(tempRestaurant)
                adapter.submitList(restaurantViewList)
            })

        queue.add(jsonObjectRequest)

         fab.setOnClickListener { view ->
            val intent = Intent()
             intent.putExtra("priceFilter", priceFilter)
             intent.putExtra("ratingFilter", ratingFilter)
             intent.putExtra("distanceFilter", distanceFilter)
             setResult(RESULT_OK, intent)
             finish()
        }
    }
}
