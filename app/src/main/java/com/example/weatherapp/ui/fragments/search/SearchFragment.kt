package com.example.weatherapp.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.presenter.SearchPresenter
import com.example.weatherapp.ui.fragments.BaseFragment
import com.example.weatherapp.ui.fragments.home.HomeFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(), ISearchView,SearchListInteraction {
    override val LOG_TAG: String = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding=FragmentSearchBinding::inflate
    private lateinit var presenter: SearchPresenter
    private var weatherData = listOf<Weather>()
    private var adapter = SearchAdapter(weatherData,this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = SearchPresenter(this)
        binding.textInputLayoutSearchHome.setEndIconOnClickListener {
            val country = binding.editTextSearchHome.text.toString()
            presenter.getSearchData(country)
        }
        binding.recyclerViewTeamTasks.adapter = adapter
    }

    private fun displayCityWeather(weatherData: List<Weather>) {
        activity?.runOnUiThread {
            this.weatherData = weatherData
            adapter.updateTasks(weatherData)
            Log.i(LOG_TAG, weatherData.toString())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showToastNotFoundError() {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
        }
    }


    override fun showLoading() {
        Log.v(LOG_TAG, "showLoading")
    }

    override fun hideLoading() {
        Log.v(LOG_TAG, "hideLoading")
    }

    override fun onSearchFailure(error: String) {
        Log.v(LOG_TAG, "onSearchFailure $error")
    }

    override fun showCityNotFoundError() {
        showToastNotFoundError()
    }

    override fun onSearchSuccess(searchData: List<Weather>) {
         displayCityWeather(searchData)
    }

    override fun onClickSearchItem(weatherData: Weather) {
        navigateToFragment(HomeFragment.newInstance(weatherData))
    }

}