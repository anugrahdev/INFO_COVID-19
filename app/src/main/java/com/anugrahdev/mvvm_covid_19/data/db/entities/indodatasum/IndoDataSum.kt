package com.anugrahdev.mvvm_covid_19.data.db.entities.indodatasum


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class IndoDataSum(
    val meninggal: String?=null,
    @PrimaryKey
    val name: String,
    val positif: String?=null,
    val sembuh: String?=null
)