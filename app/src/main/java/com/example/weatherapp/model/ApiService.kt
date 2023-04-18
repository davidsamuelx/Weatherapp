package com.example.weatherapp.model

import okhttp3.Call

interface ApiService {
    fun getCityWeather(country:String): Call
}