package com.anugrahdev.mvvm_covid_19.ui.indonesia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anugrahdev.mvvm_covid_19.data.repositories.IndoDataRepository

class IndonesiaViewModelFactory(private val repository: IndoDataRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel?> create(modelClass: Class<T>):T{
        return IndonesiaViewModel(repository) as T
    }
}
