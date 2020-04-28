package com.anugrahdev.mvvm_covid_19.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalDataItem
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataItem
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodatasum.IndoDataSum

@Database(
    entities =[GlobalDataItem::class, IndoDataItem::class, IndoDataSum::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun getGlobalDataDao():GlobalDataDao
    abstract fun getIndoDataDao():IndoDataDao
    companion object{

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        fun buildDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()

    }
}