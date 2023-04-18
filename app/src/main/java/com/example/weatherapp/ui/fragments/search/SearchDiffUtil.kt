package com.example.weatherapp.ui.fragments.search

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.model.data.Weather

class SearchDiffUtil(private val oldTasks: List<Weather>, private val newTasks: List<Weather>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldTasks.size

    override fun getNewListSize() = newTasks.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldTasks[oldItemPosition].location== newTasks[newItemPosition].location

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldTasks[oldItemPosition] == newTasks[newItemPosition]
}