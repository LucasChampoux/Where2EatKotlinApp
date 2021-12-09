package com.example.where2eatkotlinapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class MainActivity : AppCompatActivity() {
    var priceFilter : String = ""
    var distanceFilter : String = ""
    var currentLat : String = ""
    var currentLong : String = ""
    var currentLocation : Location? = null


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object{
        const val RESULTS_SELECTION = 1 + Activity.RESULT_FIRST_USER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val priceSpinner : Spinner = findViewById(R.id.priceSpinner)
        val distanceSpinner : Spinner = findViewById(R.id.distanceSpinner)
        val searchButton : Button = findViewById(R.id.searchButton)

        //get values from previous search
        if(intent.hasExtra("priceFilter")){
            priceFilter = intent.getStringExtra("priceFilter").toString()
        }
        if(intent.hasExtra("distanceFilter")){
            distanceFilter = intent.getStringExtra("distanceFilter").toString()
        }

        //Location for testing
        currentLat = "42.963588"
        currentLong = "-85.672753"

        //add values to spinner
        val priceAdapter = ArrayAdapter.createFromResource(this, R.array.priceArray, R.layout.support_simple_spinner_dropdown_item)
        priceAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        priceSpinner.adapter = priceAdapter

        val distanceAdapter = ArrayAdapter.createFromResource(this, R.array.distanceArray, R.layout.support_simple_spinner_dropdown_item)
        distanceAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        distanceSpinner.adapter = distanceAdapter

        //Selecting filter logic
        priceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long){
                if (adapterView != null) {
                    priceFilter = adapterView.getItemAtPosition(i) as String
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?){}
        }
        distanceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long){
                if (adapterView != null) {
                    distanceFilter = adapterView.getItemAtPosition(i) as String
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?){}
        }

        //button action to perform search
        searchButton.setOnClickListener { view ->

            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            intent.putExtra("priceFilter", priceFilter)
            intent.putExtra("distanceFilter", distanceFilter)
            intent.putExtra("currentLat", currentLat)
            intent.putExtra("currentLong", currentLong)
            startActivityForResult(intent, RESULTS_SELECTION)
        }
    }
        //returned values from previous screen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULTS_SELECTION && resultCode == RESULT_OK) {
            priceFilter = data?.getStringExtra("priceFilter").toString()
            distanceFilter = data?.getStringExtra("distanceFilter").toString()
        }
    }

    private fun getLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                currentLocation = location
            }
    }
}