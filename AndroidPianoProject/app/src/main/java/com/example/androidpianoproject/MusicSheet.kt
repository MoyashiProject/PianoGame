package com.example.androidpianoproject

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.androidpianoproject.ui.theme.AndroidPianoProjectTheme

class MusicSheet : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPianoProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailScreen(navController = )
                    Greeting2("Android")
                    MyScreen()
                    ShowMusicSheet()

                }

            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "ここに楽譜 $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AndroidPianoProjectTheme {
        Greeting2("Android")
    }
}
@Composable
fun ShowMusicSheet(){
    //楽譜を表示するプログラムを書く
    //データベースから楽譜を引っ張って来る
    //
}
@Composable
fun MyScreen() {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // 横画面固定時の処理
    }
}

@Composable
fun DetailScreen(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "Go Back")
    }
}
