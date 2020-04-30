package com.anugrahdev.mvvm_covid_19.ui.indonesia

import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataItem
import com.anugrahdev.mvvm_covid_19.databinding.ItemIndonesiaprovinceBinding
import com.xwray.groupie.databinding.BindableItem

class IndonesiaListItem(private val indoitem:IndoDataItem):
    BindableItem<ItemIndonesiaprovinceBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_indonesiaprovince
    }

    override fun bind(viewBinding: ItemIndonesiaprovinceBinding, position: Int) {
        viewBinding.indodata = indoitem
    }

}