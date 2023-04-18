package com.example.weatherapp.model

import okhttp3.Call

class ApiServiceImpl:ApiService {
    private val client = ApiClient()

    override fun getCityWeather(country: String): Call {
        return client.getRequest(country)
    }
}