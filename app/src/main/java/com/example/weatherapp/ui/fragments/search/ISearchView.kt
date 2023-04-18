package com.example.weatherapp.ui.fragments.search

import com.example.weatherapp.model.data.Weather

interface ISearchView {
    fun showLoading()
    fun hideLoading()
    fun onSearchFailure(error: String)
    fun showCityNotFoundError()
    fun onSearchSuccess(searchData: List<Weather>)
}