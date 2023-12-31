package com.moyashi.generatepiano

import android.app.Application
import androidx.room.Room

class RoomApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // Roomデータベース「practices」作成
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "practices"
        ).build()
    }
}