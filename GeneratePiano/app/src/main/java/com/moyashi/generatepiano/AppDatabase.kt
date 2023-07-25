package com.moyashi.generatepiano

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//practices格納
@Database(entities = [Practice::class], version = 2, exportSchema = false) //それぞれ指定 -> エンティティ、バージョン、スキーマをエキスポートするかどうか
@TypeConverters(DateTimeConverter::class,StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun practiceDao(): PracticeDao //practicesへのアクセス
}