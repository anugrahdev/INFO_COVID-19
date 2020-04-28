package com.anugrahdev.mvvm_covid_19.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT="key_saved_at"
private const val KEY_SAVED_AT_GLOBAL ="key_saved_at_global"
private const val KEY_SAVED_AT_INDOSUM ="key_saved_at_indosum"


class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun savelastSavedAtID(SavedAt:String){
        preference.edit().putString(
            KEY_SAVED_AT,
            SavedAt
        ).apply()
    }

    fun getlastSavedAtID():String?{
        return preference.getString(KEY_SAVED_AT!!, null)
    }

    fun savelastSavedAtGlobal(SavedAt:String){
        preference.edit().putString(
            KEY_SAVED_AT_GLOBAL,
            SavedAt
        ).apply()
    }

    fun getlastSavedAtGlobal():String?{
        return preference.getString(KEY_SAVED_AT_GLOBAL!!, null)
    }

    fun savelastSavedAtIndoSum(SavedAt:String){
        preference.edit().putString(
            KEY_SAVED_AT_INDOSUM,
            SavedAt
        ).apply()
    }

    fun getlastSavedAtIndoSum():String?{
        return preference.getString(KEY_SAVED_AT_INDOSUM!!, null)
    }
}