package com.moyashi.generatepiano

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PracticeViewModel, navController: NavHostController) {
    val practiceList = viewModel.retrievePracticeList()
    var text: String by remember { mutableStateOf("") }

    Column { //列で表示
        TopAppBar( //アプリバーを表示
            title = { Text("練習曲一覧") }
        )
        LazyColumn( //リストビュー表示
            modifier = Modifier
                .fillMaxWidth() //画面いっぱいに表示
                .weight(1f)
        ) {//リストビューのアイテム表示
            items(practiceList) { practice ->
                PracticeItem(practice, navController)

            }
        }
        Row( //曲を検索
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { it -> text = it },
                label = { Text("Practice") },
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    viewModel.postPractice(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("search")
            }
        }
        Row( //曲を追加
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { it -> text = it },
                label = { Text("Practice") },
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    viewModel.postPractice(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("ADD")
            }
        }
    }
}
