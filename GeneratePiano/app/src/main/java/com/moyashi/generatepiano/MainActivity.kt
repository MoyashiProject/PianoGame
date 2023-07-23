package com.moyashi.generatepiano

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.delete
import java.text.SimpleDateFormat
import java.util.Date
import androidx.navigation.compose.rememberNavController
import com.moyashi.generatepiano.ui.theme.GeneratePianoTheme

class MainActivity : ComponentActivity() {
    private val dao = RoomApplication.database.practiceDao() //practicesへのアクセス
    private var practiceList = mutableStateListOf<Practice>() // practiceListの保持
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppScreen()
        }
    }
    enum class Route { // ナビゲーションルートの定義
        FIRST,
        SECOND,
        THIRD;
    }
    private fun loadPractice() { //リスト更新
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.getALL().forEach { practice -> //practicesから全データ取得
                    practiceList.add(practice) //practicesに追加
                }
            }
        }
    }
    private fun postPractice(title: String) { //新しい演奏曲追加
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.post(Practice(id=0,title=title)) //新しい演奏曲追加

                practiceList.clear()
                loadPractice() //practiceList更新
            }
        }
    }

    private fun deletePractice(practice: Practice) { //演奏曲削除
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(practice) //演奏曲をpracticesから削除

                practiceList.clear()
                loadPractice() //practiceList更新
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyAppScreen(){
        val navController = rememberNavController()

        GeneratePianoTheme {
            Scaffold(
                bottomBar = {} // 画面遷移の操作をする（未着手）
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = Route.FIRST.name, //最初の画面をMainScreenに設定
                    modifier = Modifier.padding(padding)
                ) {
                    composable(route = Route.FIRST.name) {
                        MainScreen(practiceList)
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(practiceList: SnapshotStateList<Practice>){ //MainScreenの保持
        var text: String by remember { mutableStateOf("") } //titleの保持

        Column{//列で表示
            TopAppBar( //アプリバーを表示
                title = { Text("練習曲一覧")}
            )
            LazyColumn( //リストビュー表示
                modifier = Modifier
                    .fillMaxWidth() //画面いっぱいに表示
                    .weight(1f)
            ) {//リストビューのアイテム表示
                items(practiceList) { practice ->
                    key(practice.id) {
                        PracticeItem(practice)
                    }
                }
            }
            Row( //practiceList追加するためコード。多分この機能消す。その代わりに曲を検索できるようにする予定
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { it -> text = it },
                    label = { Text("Practice")},
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
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
                .clickable(onClick = { deletePractice(practice) }) //クリックするとpracticeItem消える
        ) {//title表示
            Text(
                text = practice.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Text( //作成日時表示
                text = "created at: ${sdf.format(practice.created_at)}",
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}

