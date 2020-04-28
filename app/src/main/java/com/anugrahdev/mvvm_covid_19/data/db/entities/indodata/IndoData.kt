package com.anugrahdev.mvvm_covid_19.data.db.entities.indodata


import com.google.gson.annotations.SerializedName

data class IndoData(
    @SerializedName("FID")
    val FID: Int,
    @SerializedName("Kasus_Meni")
    val kasusMeni: Int,
    @SerializedName("Kasus_Posi")
    val kasusPosi: Int,
    @SerializedName("Kasus_Semb")
    val kasusSemb: Int,
    @SerializedName("Kode_Provi")
    val kodeProvi: Int,
    @SerializedName("Provinsi")
    val Provinsi: String
)