package com.example.weatherapp.ui.fragments.home

import com.example.weatherapp.model.data.Weather


interface IHomeView {
    fun showLoading()
    fun hideLoading()
    fun onHomeSuccess(data:Weather)
    fun onHomeFailure(error: String)
}