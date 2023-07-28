package com.moyashi.generatepiano

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val practiceDao: PracticeDao) : ViewModel() {
    // StateFlowを使って状態を管理するプライベート変数
    private val _searchResults = MutableStateFlow<List<Practice>>(emptyList())

    // StateFlowとして公開されたsearchResultsプロパティ
    val searchResults: StateFlow<List<Practice>> get() = _searchResults.asStateFlow()

    // ユーザーの検索テキストに基づいて練習曲を検索するための関数
    fun performSearch(searchText: String) {
        // バックグラウンドで検索処理を実行するためにCoroutineを使用
        viewModelScope.launch(Dispatchers.IO) {
            // practiceDaoを使ってデータベースから検索結果を取得
            val results = practiceDao.serachPracticesByTitle(searchText)
            // StateFlowのsearchResultsに結果をセットして通知
            _searchResults.emit(results)
        }
    }
}
