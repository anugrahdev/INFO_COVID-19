package com.anugrahdev.mvvm_covid_19.ui.global

import androidx.lifecycle.ViewModel
import com.anugrahdev.mvvm_covid_19.data.repositories.GlobalDataRepository
import com.anugrahdev.mvvm_covid_19.utils.lazyDeferred

class GlobalViewModel(repository: GlobalDataRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    val globaldata by lazyDeferred {
        repository.getGlobalData()
    }

}
