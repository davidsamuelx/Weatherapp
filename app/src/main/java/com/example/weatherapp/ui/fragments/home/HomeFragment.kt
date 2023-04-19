package com.example.weatherapp.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.Constant
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.presenter.HomePresenter
import com.example.weatherapp.ui.fragments.BaseFragment
import com.example.weatherapp.ui.fragments.search.SearchFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), IHomeView{
    override val LOG_TAG: String = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
    get() = FragmentHomeBinding::inflate
    private lateinit var presenter: HomePresenter
    private var weather: Weather? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            weather = it.getSerializable(Constant.DATA) as? Weather
            setDataToUi(weather!!)
        } ?: run {
            presenter = HomePresenter(this)
            presenter.homestart()
        }

        binding.searchIcon.setOnClickListener {
            navigateToFragment(SearchFragment())
        }


    }

    private fun setDataToUi(data: Weather){
        requireActivity().runOnUiThread {
            val summerClothes = listOf(
                R.drawable.summer1,
                R.drawable.summer,
                R.drawable.summer3,
                R.drawable.summer4,
                R.drawable.summer6,
            )

            val winterClothes = listOf(
                R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
                R.drawable.winter5, R.drawable.winter6, R.drawable.winter8,
                R.drawable.winter9,
            )

            with(binding) {
                textViewCountryName.text = data.location.country
                cityNameTextView.text = data.location.name
                degree.text = data.current.temp_c.toString()
                textViewWeatherDescription.text = data.current.condition.text

                weatherCard.apply {
                    textViewWindSpeed.text = data.current.wind_kph.toString()
                    textViewUvPercent.text = data.current.uv.toString()
                    textViewCloudPercent.text = data.current.cloud.toString()
                    textViewHumidityPercent.text = data.current.humidity.toString()
                    textViewFeelsLikeDegree.text = data.current.feelslike_c.toString()
                }

                binding.weatherImage.setImageResource(
                    when (data.current.condition.text) {
                        SUNNY -> R.drawable.sunny
                        MIST -> R.drawable.mist
                        FOG -> R.drawable.mist
                        CLEAR -> R.drawable.clear
                        else -> R.drawable.cloudy
                    }
                )

                val temperature = data.current.temp_c
                binding.imageViewClothsSuggestion.setImageResource(
                    when (temperature) {
                        in WINTER_RANGE -> winterClothes.shuffled()[0]
                        in SUMMER_RANGE -> summerClothes.shuffled()[0]
                        else -> R.drawable.summer
                    }
                )
            }
        }
    }


    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }


    override fun showLoading() {
        Log.v(LOG_TAG, "showLoading")
    }

    override fun hideLoading() {
        Log.v(LOG_TAG, "hideLoading")
    }

    override fun onHomeSuccess(data: Weather) {
        Log.v(LOG_TAG, "onStartSuccess")
        weather = data
        setDataToUi(weather!!)
    }

    override fun onHomeFailure(error: String) {
        Log.v(LOG_TAG, "onStartFailure $error")
    }


    companion object {
        const val SUNNY = "Sunny"
        const val MIST = "Mist"
        const val CLEAR = "Clear"
        const val FOG = "Fog"
        val WINTER_RANGE = -100.0..25.0
        val SUMMER_RANGE = 26.0..100.0
        fun newInstance(data: Weather) = HomeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constant.DATA, data)
            }
        }
    }


}