package com.basic.mywwweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Ww
 * @date: 2021/9/10
 */
data class PlaceResponse(val status : String, val places : List<Place>)

data class Place(val name : String, val location: Location, @SerializedName("formatted_address")val address : String)

data class Location(val lng : String, val lat : String)