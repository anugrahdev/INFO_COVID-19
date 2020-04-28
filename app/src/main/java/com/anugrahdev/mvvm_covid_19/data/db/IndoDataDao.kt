package com.anugrahdev.mvvm_covid_19.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataItem
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodatasum.IndoDataSum

@Dao
interface IndoDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllIndoData(idData : List<IndoDataItem>)

    @Query("SELECT * FROM IndoDataItem")
    fun getAllIndoData() : LiveData<List<IndoDataItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveIndoSum(sumdata:IndoDataSum)

    @Query("SELECT * FROM IndoDataSum")
    fun getIndoSum(): LiveData<IndoDataSum>
}