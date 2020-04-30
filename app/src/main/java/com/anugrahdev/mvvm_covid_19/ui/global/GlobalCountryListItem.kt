package com.anugrahdev.mvvm_covid_19.ui.global

import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalDataItem
import com.anugrahdev.mvvm_covid_19.databinding.ItemGlobalcountryBinding
import com.xwray.groupie.databinding.BindableItem

class GlobalCountryListItem(private val data:GlobalDataItem): BindableItem<ItemGlobalcountryBinding>() {
    override fun getLayout() = R.layout.item_globalcountry


    override fun bind(viewBinding: ItemGlobalcountryBinding, position: Int) {
        viewBinding.globaldata = data
    }
}