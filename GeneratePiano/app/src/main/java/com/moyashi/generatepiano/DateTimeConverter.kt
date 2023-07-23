package com.moyashi.generatepiano

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        // タイムスタンプを日付に変換
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        //日付をタイムスタンプに変換
        return date?.time?.toLong()
    }
}