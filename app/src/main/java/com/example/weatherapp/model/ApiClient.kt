package com.example.weatherapp.model

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.interceptor.AuthInterceptor
import com.example.weatherapp.model.utils.RequestType
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor

class ApiClient {



    private val authInterceptor = AuthInterceptor()
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(logInterceptor)
        .build()
    private val authOkHttpClient = okHttpClient
        .newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logInterceptor)
        .build()

    fun getRequest(
        country: String? = null,
    ): Call {
        val request = request( country = country)
        return executeHttpRequest(request)
    }



    private fun request(
        method: RequestType = RequestType.GET,
        body: RequestBody? = null,
        country:String?
    ): Request {
        return Request.Builder()
            .url(BuildConfig.HOST+"?q=$country")
            .method(method.name, body)
            .build()
    }

    private fun executeHttpRequest(
        request: Request,
    ): Call {
        return authOkHttpClient.newCall(request)
    }

}