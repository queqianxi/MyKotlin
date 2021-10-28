package com.basic.mywwweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: Ww
 * @date: 2021/9/10
 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass : Class<T>) : T = retrofit.create(serviceClass)

    inline fun <reified T> create() : T = create(T::class.java)

//    声明一个变量 call 写一个「传入参数类型」是 Request 类型，「返回值类型」是 Response 类型的「函数类型声明」
//
//    lateinit var call : /* 函数类型声明 */
//
//            ——————————
//    问题补充：注意是「函数类型声明」不是「函数声明」
//    不要写成这样 fun call(res:Request):Response
}