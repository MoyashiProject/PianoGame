package com.example.androidpianoproject

import android.content.ClipData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpianoproject.ui.theme.AndroidPianoProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.delete
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {

    private val dao = RoomApplication.database.practiceDao()
    private var practiceList = mutableStateListOf<Practice>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPianoProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(practiceList)
                }
            }
        }

        loadPractice()
    }

    private fun loadPractice() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.getALL().forEach { practice ->
                    practiceList.add(practice)
                }
            }
        }
    }

    private fun postPractice(title: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.post(Practice(id=0,title=title))

                practiceList.clear()
                loadPractice()
            }
        }
    }

    private fun deletePractice(practice: Practice) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(practice)

                practiceList.clear()
                loadPractice()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(practiceList: SnapshotStateList<Practice>){
        var text: String by remember { mutableStateOf("") }
        Column{
            TopAppBar(
                title = { Text("練習曲一覧")}
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(practiceList) { practice ->
                    key(practice.id) {
                        PracticeItem(practice)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { it -> text = it },
                    label = { Text("Practice")},
                    modifier = Modifier.wrapContentHeight().weight(1f)
                )
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = {
                        if (text.isEmpty()) return@Button
                        postPractice(text)
                        text = ""
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("ADD")
                }
            }
        }
    }
    @Composable
    fun PracticeItem(practice: Practice) {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = { deletePractice(practice) })
        ) {
            Text(
                text = practice.title,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
            Text(
                text = "created at: ${sdf.format(practice.created_at)}",
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

