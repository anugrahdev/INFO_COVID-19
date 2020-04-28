package com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class GlobalData(
    val Active: Int,
    val Confirmed: Int,
    val Country_Region: String,
    val Deaths: Int,
    val Recovered: Int
)