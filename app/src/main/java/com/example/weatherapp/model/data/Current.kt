package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Current(
    val temp_c:Double,
    val temp_f:Double,
    val condition: Condition,
    @SerializedName("wind_mph")
    val wind_mph: Double,
    @SerializedName("wind_kph")
    val wind_kph: Double,
    @SerializedName("wind_degree")
    val wind_degree: Double,
    @SerializedName("wind_dir")
    val wind_dir: String,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("feelslike_c")
    val feelslike_c: Double,
    @SerializedName("feelslike_f")
    val feelslike_f: Double,
    @SerializedName("cloud")
    val cloud: Double,
    @SerializedName("uv")
    val uv :Double
)
