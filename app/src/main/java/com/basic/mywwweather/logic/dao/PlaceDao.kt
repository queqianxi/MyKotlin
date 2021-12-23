package com.basic.mywwweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.basic.mywwweather.App
import com.basic.mywwweather.logic.model.Place
import com.google.gson.Gson

/**
 * @author: Ww
 * @date: 2021/11/10
 */
object PlaceDao {

    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace() : Place{
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = App.context.getSharedPreferences("my_weather", Context.MODE_PRIVATE)
}