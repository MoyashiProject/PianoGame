package com.moyashi.generatepiano

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyashi.generatepiano.backgroundTask.GenerateScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class PracticeViewModel : ViewModel() {
    private val dao = RoomApplication.database.practiceDao()
    val practiceList: SnapshotStateList<Practice> = mutableStateListOf<Practice>()

    init {
        loadPractice()
    }

    fun postPractice(title: String, hardMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            val scores :List<String>

            if(hardMode){
                scores = GenerateScore().GenerateHard()
                println("難しいモード")
            }else{
                scores = GenerateScore().GenerateEasy()

            }
            val newPractice = Practice(id = 0, title = title, created_at = Date(),hardMode,scores,scores)
            dao.post(newPractice)
            loadPractice()
        }
    }

    fun retrievePracticeList(): List<Practice> {
        return practiceList
    }

    fun deletePractice(practice: Practice) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(practice)
            loadPractice()
        }
    }

    fun retrievePracticeById(practiceId: Long): Practice? {
        return practiceList.find { it.id == practiceId }
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