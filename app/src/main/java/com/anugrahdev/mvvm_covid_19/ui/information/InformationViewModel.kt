package com.anugrahdev.mvvm_covid_19.ui.information

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.anugrahdev.mvvm_covid_19.R
import kotlinx.android.synthetic.main.fragment_information.*

class InformationViewModel: ViewModel() {
    fun onClickInfo(view : View){
        view.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_informationFragment_to_covidFragment)
        }
    }
}