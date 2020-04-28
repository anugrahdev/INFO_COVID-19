package com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata


import androidx.room.Embedded
import androidx.room.Entity
import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalData

@Entity(primaryKeys = ["Country_Region"])
data class GlobalDataItem(
    @Embedded()
    val attributes: GlobalData
)