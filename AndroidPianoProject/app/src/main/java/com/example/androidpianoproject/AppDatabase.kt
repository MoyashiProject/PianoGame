package com.example.androidpianoproject

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//practices格納
@Database(entities = [Practice::class], version = 1, exportSchema = false) //それぞれ指定 -> エンティティ、バージョン、スキーマをエキスポートするかどうか
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun practiceDao(): PracticeDao //practicesへのアクセス
}