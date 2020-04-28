package com.anugrahdev.mvvm_covid_19.ui.indonesia

import androidx.lifecycle.ViewModel
import com.anugrahdev.mvvm_covid_19.data.repositories.IndoDataRepository
import com.anugrahdev.mvvm_covid_19.utils.lazyDeferred

class IndonesiaViewModel(private val repository:IndoDataRepository): ViewModel() {
    // TODO: Implement the ViewModel

    val indodata by lazyDeferred {
        repository.getIndoData()
    }

    val indosum by lazyDeferred{
        repository.getIndoSum()
    }
}
