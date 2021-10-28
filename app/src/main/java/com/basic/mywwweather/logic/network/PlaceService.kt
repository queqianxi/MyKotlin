package com.basic.mywwweather.logic.network

import com.basic.mywwweather.App
import com.basic.mywwweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: Ww
 * @date: 2021/9/10
 */
interface PlaceService {

    @GET("v2/place?token=${App.WEATHER_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query")query : String) : Call<PlaceResponse>
}