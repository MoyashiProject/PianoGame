package com.example.androidpianoproject

import androidx.room.*


@Dao
interface PracticeDao {
    @Query("select * from practices order by created_at asc")
    fun getALL(): MutableList<Practice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(practice: Practice)

    @Delete
    fun delete(practice: Practice)
}