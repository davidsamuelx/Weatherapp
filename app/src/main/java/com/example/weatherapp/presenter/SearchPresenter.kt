package com.example.weatherapp.presenter

import com.example.weatherapp.model.ApiServiceImpl
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.ui.fragments.search.ISearchView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class SearchPresenter(
    private val view: ISearchView
){
    private val apiService = ApiServiceImpl()

    fun getSearchData(country:String){
        view.showLoading()
        apiService.getCityWeather(country).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.onSearchFailure(e.toString())
                view.hideLoading()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val data = Gson().fromJson(jsonString, Weather::class.java)
                    val weatherData = listOf(data)
                    if (response.code == 400){ view.showCityNotFoundError() }
                    view.onSearchSuccess(weatherData)
                }
                view.hideLoading()
            }
        })
    }
}