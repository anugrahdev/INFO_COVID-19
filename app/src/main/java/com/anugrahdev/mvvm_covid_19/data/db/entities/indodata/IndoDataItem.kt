package com.anugrahdev.mvvm_covid_19.data.db.entities.indodata


import androidx.room.Embedded
import androidx.room.Entity
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoData

@Entity(primaryKeys = ["FID"])
data class IndoDataItem(
    @Embedded
    val attributes: IndoData
)