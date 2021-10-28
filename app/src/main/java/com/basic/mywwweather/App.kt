package com.basic.mywwweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @author: Ww
 * @date: 2021/9/10
 */
class App : Application(){

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val WEATHER_TOKEN = "获取到的令牌值"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}