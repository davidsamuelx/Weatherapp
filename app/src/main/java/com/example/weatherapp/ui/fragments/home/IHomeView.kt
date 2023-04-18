package com.example.weatherapp.ui.fragments.home


interface IHomeView {
    fun showLoading()
    fun hideLoading()
    fun onHomeSuccess()
    fun onHomeFailure(error: String)
}