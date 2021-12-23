package com.basic.mywwweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author: Ww
 * @date: 2021/10/28
 */
object SunnyWeatherNetWork {

    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query : String) = placeService.searchPlaces(query).await()

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    /**声明为Call的扩展函数；suspend声明为挂起函数，可以在协程中调用；
     * suspendCoroutine在挂起函数或协程中调用，会立即阻塞当前协程，让执行
     * 代码在普通线程中调用，然后调用continuation来回复当前协程*/
    private suspend fun <T> Call<T>.await() : T{
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                            RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}