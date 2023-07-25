package com.moyashi.generatepiano

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MusicDetailScreen(practice: Practice?, navController: NavHostController, viewModel: PracticeViewModel) {
    //MainScrennでクリックされたPracticeItemの詳細を表示
    //画面下で削除か演奏のボタンを選択
    val selectedPractice by remember { mutableStateOf(practice) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
        ) {
            if (practice != null) {
                Text(
                    text = practice.title,
                    style = typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "作成日時 : ${practice.created_at}",
                    style = typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text="右手:${practice.right_hand}",
                    style = typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            } else {
                Text("Practice not found.")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = {
                    navController.popBackStack(MainActivity.Route.FIRST.name, inclusive = false)
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("戻る")
            }
            Button(
                onClick = {
                    navController.navigate(MainActivity.Route.FOURTH.name)
                }
            ) {
                Text("練習する")
            }
            Button(
                onClick = {
                    selectedPractice?.let { practiceToDelete ->
                        viewModel.deletePractice(practice = practiceToDelete)
                        navController.navigate((MainActivity.Route.FIRST.name))
                    }
                }
            ) {
                Text("削除する")
            }
        }
    }
}