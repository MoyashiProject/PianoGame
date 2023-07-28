package com.moyashi.generatepiano.backgroundTask

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moyashi.generatepiano.DetectSoundViewModel
import com.moyashi.generatepiano.backgroundTask.ui.theme.GeneratePianoTheme

class testActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this


        val viewModel =
            ViewModelProvider(this)[DetectSoundViewModel::class.java]
        val sound = CollectSoundStream(context =context,viewModel)
        sound.start(10)


        setContent {
           Greeting(viewModel = viewModel)
        }
    }
    @Composable
    fun Greeting(viewModel: DetectSoundViewModel) {
        val counter = viewModel.onkai.observeAsState()
        var text: String by remember { mutableStateOf("") }
        GeneratePianoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = {
                        val generated = GenerateScore().GenerateEasy()
                        text = generated.toString()
                    }) {
                        Text("楽譜の生成")
                    }
                    Text(text=text)
                    counter.value?.let { count ->

                        Text("$count", textAlign = TextAlign.Center,modifier = Modifier.width(150.dp), fontSize = 50.sp)
                    }
                }
            }
        }
    }
}


