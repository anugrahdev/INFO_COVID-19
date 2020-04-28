package com.anugrahdev.mvvm_covid_19.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anugrahdev.mvvm_covid_19.data.db.AppDatabase
import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalDataItem
import com.anugrahdev.mvvm_covid_19.data.network.ApiServiceKC
import com.anugrahdev.mvvm_covid_19.data.network.SafeApiRequest
import com.anugrahdev.mvvm_covid_19.data.preferences.PreferenceProvider
import com.anugrahdev.mvvm_covid_19.utils.NoInternetException
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

private val MINIMUM_REFRESH_INTERVAL = 6

class GlobalDataRepository (private val api:ApiServiceKC,
                            private val db:AppDatabase,
                            private val prefs: PreferenceProvider
): SafeApiRequest() {

//    suspend fun getGlobal() = apiRequest { api.getCovidDataGlobal() }
    private val  globaldata = MutableLiveData<List<GlobalDataItem>>()

    init {
        // Live data akan di save ke dalam lokal database
        globaldata.observeForever {
            saveGlobalData(it)
        }
    }

    private fun saveGlobalData(global: List<GlobalDataItem>){
        //save quotes data to local database
        Coroutines.io {
            prefs.savelastSavedAtGlobal(LocalDateTime.now().toString())
            db.getGlobalDataDao().saveAllGlobalData(global)
        }
    }

    suspend fun getGlobalData(): LiveData<List<GlobalDataItem>> {
        return withContext(Dispatchers.IO) {
            fetchData()
            db.getGlobalDataDao().getAllGlobalData()
        }
    }

    private suspend fun fetchData(){
        var lastSavedAt = prefs.getlastSavedAtGlobal()
        if (lastSavedAt==null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            //fetch the quotes from api
            try{
                val response= apiRequest { api.getCovidDataGlobal() }
                //post quotes value to the quotes live data
                globaldata.postValue(response)
            }catch (e:NoInternetException){

            }

        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime):Boolean{
        return ChronoUnit.HOURS.between(savedAt,LocalDateTime.now())> MINIMUM_REFRESH_INTERVAL;
    }


}