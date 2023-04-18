package com.example.weatherapp.ui.fragments.search

import com.example.weatherapp.model.data.Weather

interface SearchListInteraction {

    fun onClickSearchItem(weatherData: Weather)
}