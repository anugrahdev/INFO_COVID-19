package com.anugrahdev.mvvm_covid_19.ui.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anugrahdev.mvvm_covid_19.data.repositories.GlobalDataRepository
import com.anugrahdev.mvvm_covid_19.ui.indonesia.IndonesiaViewModel

class GlobalViewModelFactory(private val repository: GlobalDataRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel?> create(modelClass: Class<T>):T{
        return GlobalViewModel(repository) as T
    }
}