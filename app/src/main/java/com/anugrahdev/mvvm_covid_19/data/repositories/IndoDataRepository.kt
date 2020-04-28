package com.anugrahdev.mvvm_covid_19.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anugrahdev.mvvm_covid_19.data.db.AppDatabase
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataItem
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodatasum.IndoDataSum
import com.anugrahdev.mvvm_covid_19.data.network.ApiServiceKC
import com.anugrahdev.mvvm_covid_19.data.network.SafeApiRequest
import com.anugrahdev.mvvm_covid_19.data.preferences.PreferenceProvider
import com.anugrahdev.mvvm_covid_19.ui.indonesia.IndoDataListener
import com.anugrahdev.mvvm_covid_19.utils.NoInternetException
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

private val MINIMUM_REFRESH_INTERVAL = 6
class IndoDataRepository (
    private val api: ApiServiceKC,
    private val db: AppDatabase,
    private val prefs:PreferenceProvider
): SafeApiRequest(){
    private val indoData = MutableLiveData<List<IndoDataItem>>()
    private val indoSum = MutableLiveData<IndoDataSum>()

    init {
        indoData.observeForever{
            saveIndoData(it)
        }

        indoSum.observeForever{
            saveIndoSum(it)
        }
    }

    private fun saveIndoData(idData : List<IndoDataItem>){
        Coroutines.io{
            prefs.savelastSavedAtID(LocalDateTime.now().toString())
            db.getIndoDataDao().saveAllIndoData(idData)
        }
    }

    private fun saveIndoSum(idSum : IndoDataSum){
        Coroutines.io{
            prefs.savelastSavedAtIndoSum(LocalDateTime.now().toString())
            db.getIndoDataDao().saveIndoSum(idSum)
        }
    }

    suspend fun getIndoData(): LiveData<List<IndoDataItem>>{
        return withContext(Dispatchers.IO){
            fetchData()
            db.getIndoDataDao().getAllIndoData()
        }
    }

    suspend fun getIndoSum(): LiveData<IndoDataSum>{
        return withContext(Dispatchers.IO){
            fetchDataSum()
            db.getIndoDataDao().getIndoSum()
        }
    }

    suspend private fun fetchData(){
        var lastSaved = prefs.getlastSavedAtID()
        if (lastSaved==null || isFetchNeeded(LocalDateTime.parse(lastSaved))){
            try {
                val response = apiRequest { api.getCovidIndoData() }
                indoData.postValue(response)
            }catch (e:NoInternetException){
            }
        }
    }

    suspend private  fun fetchDataSum(){
        var lastSaved = prefs.getlastSavedAtIndoSum()
        if (lastSaved==null || isFetchNeeded(LocalDateTime.parse(lastSaved))){
            try{
                val response = apiRequest { api.getCovidIndoSum() }
                indoSum.postValue(response.last())
            }catch (e:NoInternetException){
            }

        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime):Boolean{
        return ChronoUnit.HOURS.between(savedAt,LocalDateTime.now())> MINIMUM_REFRESH_INTERVAL;
    }

}