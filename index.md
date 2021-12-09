# Where2Eat
## A Demo Kotlin app demonstrating Google Places Nearby Search API
This tutorial assumes you have familiarity with basic Android functionality

### Overview
I researched Googles Places API so you don't have to! This has a focus on android development, but the API is universal and the information gained here can be applied to multiple platforms.

The goal of this app is to display nearby restaurants that fit certain criteria set by the user. These criteria include price, location, and distance.

### Getting started
The first step to getting started with the Google Places API is to set up an API key [here](https://console.cloud.google.com), Google's API Platform. Set up an account here before proceeding
  
 _Do note that Google does require billing information to use their APIs._

_For further guidance on specifially getting set up on Google's API platform, see [this](https://developers.google.com/maps/documentation/places/web-service/cloud-setup) tutorial created by them. You will want to enable the Places API for this project_ 

This app was created in Android Studio Arctic Fox. This app requires a few additions to the AndroidManifest.xml. See those below:
```
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.INTERNET" />
```
These additions to the manifest allow the app to gain location and internet privledges.

We will also use a library known as Volley to handle our https requests to the API. To access the library, add the following to your Gradle Build Script:
```
  implementation 'com.android.volley:volley:1.2.1'
```
For more information on Volley, please visit the [documentation](https://developer.android.com/training/volley)

## Implementation
After you have added all the neccessary dependencies and gotten your API key, you are ready to start coding.

### Step One 
Import statements within our Kotlin file.

>In the file where you wish to call the API, you must include the following import statements at the top of the Kotlin file:
```
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
```

>"com.android.volley" stores all of Volley, but we only grabbed what was needed to save on resources. 

>We require "import org.json.JSONArray" and "import org.json.JSONObject" as we will be working with JSON arrays and objects in our response.

### Step Two
Create the Volley object and request.

>There are 3 parts that we will need to utilize Volley:
>1. a url
>1. a request queue
>2. a JSONObject Requester

>The url is the request sent to the googple places API. An example of a request is as follows:
```
https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?keyword=" +
                "&location=$currentLat%2C$currentLong" +
                "&radius=$distVal" +
                "&type=restaurant" +
                "&maxprice=$priceVal" +
                "&opennow=true" +
                "&key=YOUR_API_KEY"
```             

>Parameters are seperated with a '&' symbol. For a more detailed view of all the different parameters, please refer to [this](https://developers.google.com/maps/documentation/places/web-service/search-nearby#PlaceSearchRequests) page

>The request queue will handle submitting the http request. The JSONObject Requester will import the JSON response from that http request.

>Creating a queue is as follows:
```
val queue = Volley.newRequestQueue(this)
```
>The queue takes an input of type Context, 'this" represents that in the code snippet above.

>Creating a JSONObject Requester requires a bit more work. The code snippet below shows an example of how I parsed data.
```
val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                val status = response.getString("status")
                if( status != "OK"){
                    //Logic for if request was unsuccessful.
                }
                else {
                    //Logic for if request was successful and returned results
                    //Put any parsing code and or any code that is reliant on your data within this block
                    }
                    
                }
            },
            { error ->
                //Logic for error message handling
            })
```         
>Do note that because this works asyncrounously. You need to include any work with the data inside the lambda function so that data can be parsed when it is received.

### Step 3
Sending the request.

>Finally, to hook them up is as simple as:
```
queue.add(jsonObjectRequest)
```
>Result is sent as a JSONObject, response, within the JSONOBjectRequest. Please see [this](https://developers.google.com/maps/documentation/places/web-service/search-nearby#PlacesNearbySearchResponse) for more information on the data sent upon request.

## Conclusion and notes
At this point, you have the information available to you to be able to implement the Google Places Nearby Search API. I would recommend doing more research into parsing JSON as that was not covered. A good for parsing JSON in Kotlin is the [Android documentation](https://developer.android.com/reference/kotlin/org/json/JSONObject). If you wish to use current location data
