package com.moyashi.generatepiano.backgroundTask

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moyashi.generatepiano.DetectSoundViewModel
import com.moyashi.generatepiano.backgroundTask.ui.theme.GeneratePianoTheme

class testActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        val sound = CollectSoundStream(context =context)

        val viewModel =
            ViewModelProvider(this)[DetectSoundViewModel::class.java]
//        sound.start(10)


        setContent {
           Greeting(viewModel = viewModel)
        }
    }
    @Composable
    fun Greeting(viewModel: DetectSoundViewModel) {
        val counter = viewModel.counter.observeAsState()
        var text: String by remember { mutableStateOf("") }
        GeneratePianoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column() {
                    Button(onClick = {
                        val generated = GenerateScore().GenerateEasy()
                        text = generated.toString()
                    }) {
                        counter.value?.let { count ->
                            Text("$count")
                        }
                    }
                    Text(text=text)
                }
            }
        }
    }
}


