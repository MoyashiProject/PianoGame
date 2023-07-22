package com.example.androidpianoproject

import androidx.room.*
import java.util.*

@Entity(tableName = "practices")
data class Practice(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val created_at: Date = Date()
)