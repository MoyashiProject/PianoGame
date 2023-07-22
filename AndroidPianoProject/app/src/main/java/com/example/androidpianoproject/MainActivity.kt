package com.example.androidpianoproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidpianoproject.ui.theme.AndroidPianoProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPianoProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val practiceList = (1..100).map {"練習曲${it}"}
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    var practiceList: SnapshotStateList<String> by remember { mutableStateOf(mutableStateListOf())}
    var text: String by remember { mutableStateOf("") }
    Column{
        TopAppBar(
            title = { Text("練習曲一覧")}
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(practiceList) { practice ->
                Text(
                    text = practice,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { it -> text = it },
                label = { Text("Practice")},
                modifier = Modifier.wrapContentHeight().weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    practiceList.add(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }
    }
}