package com.moyashi.generatepiano

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PracticeViewModel : ViewModel() {
    private val dao = RoomApplication.database.practiceDao()
    val practiceList: SnapshotStateList<Practice> = mutableStateListOf<Practice>()

    init {
        loadPractice()
    }

    fun postPractice(title: String) {
        viewModelScope.launch {
            val newPractice = Practice(id = 0, title = title)
            dao.post(newPractice)
            loadPractice()
        }
    }

    fun deletePractice(practice: Practice) {
        viewModelScope.launch {
            dao.delete(practice)
            loadPractice()
        }
    }

    fun retrievePracticeList(): SnapshotStateList<Practice> {
        return practiceList
    }

    private fun loadPractice() {
        viewModelScope.launch {
            val practices = withContext(Dispatchers.Default) {
                dao.getALL()
            }
            practiceList.clear()
            practiceList.addAll(practices)
        }
    }
}
