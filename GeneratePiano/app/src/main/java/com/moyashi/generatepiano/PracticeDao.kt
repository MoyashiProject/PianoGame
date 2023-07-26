package com.moyashi.generatepiano
import androidx.room.*


@Dao
interface PracticeDao {
    @Query("select * from practices order by created_at asc") //practicesから作成日時を昇順で取得
    fun getALL(): MutableList<Practice>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //practicesに要素追加、既にある場合は上書き
    fun post(practice: Practice)

    @Delete
    fun delete(practice: Practice) //practicesの削除

    @Query("SELECT * FROM practices WHERE title LIKE '%' || :searchText || '%'")
// practicesテーブルから、title列が検索テキストに部分一致する練習曲を取得するためのクエリ
    fun serachPracticesByTitle(searchText: String): List<Practice>
// 検索テキストを受け取り、結果として該当する練習曲のリストを返す関数
}