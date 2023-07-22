package com.example.androidpianopractice

import android.Manifest
import android.app.Activity
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.PersistableBundle
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import kotlinx.coroutines.*

class CollectSoundStream(val context:Context){
    private val SAMPLE_RATE = 44100
    private val ENCODING = AudioFormat.ENCODING_PCM_16BIT
    private val CHANNEL = AudioFormat.CHANNEL_IN_MONO

    private var isRecording = false
    val streamRoutine = GlobalScope.launch{
        Log.d("おはよう","押された。")
        
    }

}

