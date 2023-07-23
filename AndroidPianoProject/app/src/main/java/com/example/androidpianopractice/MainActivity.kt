package com.example.androidpianopractice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.MutableLiveData
import com.example.androidpianopractice.ui.theme.AndroidPianoPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context:Context = this
        setContent {
            AndroidPianoPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DetectSoundAcitivity(context = context)
                }
            }
        }
    }
}

@Composable
fun DetectSoundAcitivity(context:Context) {

    val isLimit: MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }

    Column {
        Button(onClick = {
            val sound = CollectSoundStream(context = context)
            sound.start(10)
        }) {
            Text(text = "おはよう")
        }

    }


}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AndroidPianoPracticeTheme {
//        Greeting("Android")
//    }
//}