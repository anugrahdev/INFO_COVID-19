package com.anugrahdev.mvvm_covid_19.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalDataItem

@Dao
interface GlobalDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllGlobalData(globaldata: List<GlobalDataItem>)

    @Query("SELECT * FROM GlobalDataItem" )
    fun getAllGlobalData():  LiveData<List<GlobalDataItem>>
}