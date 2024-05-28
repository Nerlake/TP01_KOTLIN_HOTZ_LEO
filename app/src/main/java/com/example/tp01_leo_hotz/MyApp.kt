package com.example.tp01_leo_hotz

import android.app.Application
import androidx.room.Room
import com.example.tp01_leo_hotz.datas.AppDatabase

class MyApp : Application() {
    companion object {
        val database: AppDatabase by lazy {
            Room.databaseBuilder(
                instance.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
        }
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
