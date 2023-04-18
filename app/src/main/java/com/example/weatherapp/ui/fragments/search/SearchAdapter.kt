package com.example.weatherapp.ui.fragments.search


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemSearchCardBinding
import com.example.weatherapp.model.data.Weather

class SearchAdapter (private var searchList: List<Weather>, private val listener: SearchListInteraction):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_card, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        try {
            val currentItem = searchList[position]
            holder.binding.apply {
                textViewCityName.text = currentItem.location.name
                textViewLocalTime.text = currentItem.location.localtime
                textViewTemperatureDegree.text = currentItem.current.temp_c.toString()
                textViewWeatherDescription.text = currentItem.current.condition.text
                root.setOnClickListener { listener.onClickSearchItem(searchList[0]) }
                val date = currentItem.location.localtime
                when (date.substringAfter(" ").substringBefore(":").toInt()) {
                    in 6..11 -> {
                        imageViewDayTimeState.setImageResource(R.drawable.morning)
                    }
                    in 12..17 -> {
                        imageViewDayTimeState.setImageResource(R.drawable.evening)
                    }
                    in 18..23, in 0..5 -> {
                        imageViewDayTimeState.setImageResource(R.drawable.night)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("test", "Error in onBindViewHolder(): ${e.message}")
        }
    }

    override fun getItemCount() = searchList.size

    class SearchViewHolder(viewItem: View):RecyclerView.ViewHolder(viewItem){
        val binding = ItemSearchCardBinding.bind(itemView)
    }

    fun updateTasks(newTask: List<Weather>){
        val diffResult = DiffUtil.calculateDiff(SearchDiffUtil(searchList, newTask))
        searchList = newTask
        diffResult.dispatchUpdatesTo(this)
    }

}