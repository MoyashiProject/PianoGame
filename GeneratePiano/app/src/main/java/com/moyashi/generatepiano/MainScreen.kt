package com.moyashi.generatepiano

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: PracticeViewModel,
    navController: NavHostController
) {
    val practiceList = viewModel.retrievePracticeList()


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    var searchText: String by remember { mutableStateOf("") }
    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val fontFamily = FontFamily(
        Font(R.font.drumfont)
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text("ピアノのれんしゅう",fontFamily= fontFamily, fontSize = 23.sp, style = MaterialTheme.typography.headlineMedium,) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    titleContentColor=MaterialTheme.colorScheme.onSurface
                ),

                actions = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Share, null)
                    }
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Settings, null)
                    }
                })
        },
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
            Column(modifier= Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)){
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        viewModel.loadIdentifyPractice(newText)
                    },
                    label = { Text("けんさく",fontFamily = fontFamily)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp, horizontal = 12.dp),
                )
            }

            ModalBottomSheetLayout(sheetContent = { BottomSheet(viewModel)}, sheetState = sheetState, modifier = Modifier.fillMaxSize()) {
                if(viewModel.practiceList.isEmpty()){
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "ピアノデータを早速作ってモテよう！")

                    }
                }else{
                    LazyColumn( //リストビュー表示
                        modifier = Modifier
                            .fillMaxWidth() //画面いっぱいに表示
                            .weight(1f),
                    ) {//リストビューのアイテム表示
                            items(practiceList) { practice ->
                                Card(modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .border(1.dp, Color.Black)
                                ){
                                    PracticeItem(practice, navController)
                                }   
                            }
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
        val selectHard: MutableState<Boolean> = remember { mutableStateOf(false) }

        // 水平方向にレイアウトするRow
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // ラジオボタン1
            Box(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(16.dp),
                contentAlignment = Alignment.Center){
                Row{
                    Text("難しい")
                    RadioButton(
                        selected = selectHard.value, // 選択されたかどうか
                        onClick = { selectHard.value = true }, // クリックされたときの処理
                    )
                }
            }
            Box(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(16.dp),
                contentAlignment = Alignment.Center){
                Row{
                    Text(text = "簡単")
                    RadioButton(
                        selected = selectHard.value == false, // 選択されたかどうか
                        onClick = { selectHard.value = false }, // クリックされたときの処理
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { it -> text = it },
                label = { Text("名前をつけてください") },
                modifier = Modifier.fillMaxWidth() // OutlinedTextFieldを画面の横全体に広げる
            )
            Button(
                onClick = {
                    if (text.isEmpty()) return@Button
                    viewModel.postPractice(text,selectHard.value)
                    text = ""
                },
                modifier = Modifier.padding(vertical = 16.dp) // 上下に16dpの余白を追加して中央に配置する
            ) {
                Text("作成")
            }
        }

    }
}
