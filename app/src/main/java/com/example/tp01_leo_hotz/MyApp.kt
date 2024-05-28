package com.example.tp01_leo_hotz

import android.app.Application
import androidx.room.Room
import com.example.tp01_leo_hotz.datas.AppDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
