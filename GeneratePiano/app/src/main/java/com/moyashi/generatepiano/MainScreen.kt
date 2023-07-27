package com.moyashi.generatepiano

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: PracticeViewModel, navController: NavHostController) {
    val practiceList = viewModel.retrievePracticeList()


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text("練習曲一覧")
            },
        ) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                    else sheetState.show()
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        }
    ) {
        Column {
            ModalBottomSheetLayout(sheetContent = { BottomSheet(viewModel)}, sheetState = sheetState, modifier = Modifier.fillMaxSize()) {
                LazyColumn( //リストビュー表示
                    modifier = Modifier
                        .fillMaxWidth() //画面いっぱいに表示
                        .weight(1f)
                ) {//リストビューのアイテム表示
                    items(practiceList) { practice ->
                        PracticeItem(practice, navController)
                    }
                }
            }//列で表示

        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: PracticeViewModel) {
    var text: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "新規曲作成",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        val selectedOption = remember { mutableStateOf("Option1") }

        // 水平方向にレイアウトするRow
        Row(
            modifier = Modifier.padding(16.dp), // paddingを設定
            horizontalArrangement = Arrangement.SpaceEvenly // 子要素の間隔を均等にする
        ) {
            // ラジオボタン1
            Text("難しい")
            RadioButton(
                selected = selectedOption.value == "Option1", // 選択されたかどうか
                onClick = { selectedOption.value = "Option1" }, // クリックされたときの処理
            )

            // ラジオボタン2
            Text("簡単")
            RadioButton(
                selected = selectedOption.value == "Option2", // 選択されたかどうか
                onClick = { selectedOption.value = "Option2" } // クリックされたときの処理
            )
        }
        OutlinedTextField(
            value = text,
            onValueChange = { it -> text = it },
            label = { Text("Practice") },
        )
        Button(
            onClick = {
                if (text.isEmpty()) return@Button
                viewModel.postPractice(text)
                text = ""
            },
        ) {
            Text("ADD")
        }
    }
}
