package com.anugrahdev.mvvm_covid_19

import android.app.Application
import com.anugrahdev.mvvm_covid_19.data.db.AppDatabase
import com.anugrahdev.mvvm_covid_19.data.network.ApiServiceKC
import com.anugrahdev.mvvm_covid_19.data.network.NetworkInterceptor
import com.anugrahdev.mvvm_covid_19.data.preferences.PreferenceProvider
import com.anugrahdev.mvvm_covid_19.data.repositories.GlobalDataRepository
import com.anugrahdev.mvvm_covid_19.data.repositories.IndoDataRepository
import com.anugrahdev.mvvm_covid_19.ui.global.GlobalViewModelFactory
import com.anugrahdev.mvvm_covid_19.ui.indonesia.IndonesiaViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { ApiServiceKC(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { IndoDataRepository(instance(),instance(), instance()) }
        bind() from singleton { GlobalDataRepository(instance(),instance(), instance())}
        bind() from provider { IndonesiaViewModelFactory(instance()) }
        bind() from provider { GlobalViewModelFactory(instance()) }



    }
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }

}