package com.moyashi.generatepiano

import androidx.room.*
import java.util.*

@Entity(tableName = "practices") //テーブルネームがpracticesというエンティティ
data class Practice(
    @PrimaryKey(autoGenerate = true) //プライマリーキー自動生成
    //ID、タイトル、作成日時を保存
    val id: Long,
    val title: String,
    val created_at: Date = Date(),
    @TypeConverters(StringListTypeConverter::class) // Converterの指定
    val right_hand: List<String>,
    val left_hand: List<String>
)
