package com.example.weatherapp.presenter

import com.example.weatherapp.model.ApiServiceImpl
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.ui.fragments.home.IHomeView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HomePresenter(
    private val view:IHomeView
) {
    private val apiService = ApiServiceImpl()

    fun homestart(country: String = "cairo"){
        view.showLoading()
        apiService.getCityWeather(country).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.onHomeFailure(e.toString())
                view.hideLoading()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val data = Gson().fromJson(jsonString, Weather::class.java)
                    view.onHomeSuccess()
                }
                view.hideLoading()
            }
        })
    }
}