package com.example.androidpianoproject

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Practice::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun practiceDao(): PracticeDao
}