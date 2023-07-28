package com.moyashi.generatepiano.backgroundTask

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.moyashi.generatepiano.DetectSoundViewModel
import com.moyashi.generatepiano.enum.enum_musicStep
import org.jtransforms.fft.DoubleFFT_1D
import java.util.stream.IntStream
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.sqrt

class CollectSoundStream(val context: Context,val detectSoundViewModel: DetectSoundViewModel) : ComponentActivity() {
    companion object {
        const val LOG_NAME: String = "AudioSensor"
        fun findNearestEnumByFrequency(frequency: Double): enum_musicStep? {
            return enum_musicStep.values().minByOrNull { Math.abs(it.frequency - frequency) }
        }
    }
    private val sampleRate = 44100 // サンプリングレート
    // 音データのサイズ
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate, AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    lateinit var audioRecord: AudioRecord
    private var buffer:ShortArray = ShortArray(bufferSize) // バッファの収容先

    private var isRecoding: Boolean = false // 録音しているか
    private var run: Boolean = false // 音解析をしているか

    private var nowDB: Int = 0 //今のデシベルの値

    // 録音開始時の初期設定
    // period: オーディオ処理のインターバル
    fun start(period: Int) {
        // permissionチェック
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        // 録音開始
        audioRecord.startRecording()

        isRecoding = true
        // 指定period[ms]ごとにrecordingModeで指定した処理を実行
        if (!run) {
            recoding(period)
            recodingFrequency(period)
            recodingDB(period)
        }
    }

    // AudioRecordから定期的にデータをもらう
    private fun recoding(period: Int) {
        val hnd0 = Handler(Looper.getMainLooper())
        run = true
        // こいつ(rnb0) が何回も呼ばれる
        val rnb0 = object : Runnable {
            override fun run() {
                // bufferにデータを入れる
                audioRecord.read(buffer,0,bufferSize)
                // 振幅が出る
                Log.d(LOG_NAME,"${buffer[100]}, ${buffer[300]}")

                // stop用のフラグ
                if (run) {
                    // 指定時間後に自分自身を呼ぶ
                    hnd0.postDelayed(this, period.toLong())
                }
            }
        }
        // 初回の呼び出し
        hnd0.post(rnb0)
    }

    // デシベル変換したやつを出力
    private fun recodingDB(period: Int) {
        val hnd0 = Handler(Looper.getMainLooper())
        run = true
        val rnb0 = object : Runnable {
            override fun run() {
                // bufferにデータを入れる
                audioRecord.read(buffer,0,bufferSize)

                // 最大音量を解析
                val sum = buffer.sumOf { it.toDouble() * it.toDouble() }
                val amplitude = sqrt(sum / bufferSize)
                // デシベル変換
                val db = (20.0 * log10(amplitude)).toInt()
                Log.d(LOG_NAME,"db = $db")
                nowDB = db
                if (run) {
                    hnd0.postDelayed(this, period.toLong())
                }
            }
        }
        hnd0.post(rnb0)
    }

    private fun recodingFrequency(period: Int){
        val hnd0 = Handler(Looper.getMainLooper())
        run = true
        // こいつ(rnb0) が何回も呼ばれる
        val rnb0 = object : Runnable {
            override fun run() {
                // bufferにデータが入る
                audioRecord.read(buffer,0,bufferSize)

                // FFT 結果はfftBufferに入る
                val fft = DoubleFFT_1D(bufferSize.toLong())
                val fftBuffer = buffer.map { it.toDouble() }.toDoubleArray()
                fft.realForward(fftBuffer)


                // 特定の周波数の振幅値の解析
                val targetFrequency = 10000 // 特定の周波数（Hz）
                val index = (targetFrequency * fftBuffer.size / sampleRate).toInt() // 特定の周波数が入っているリスト番号
                // 振幅値の解析
                val amplitude = sqrt((fftBuffer[index] * fftBuffer[index] + fftBuffer[index + 1] * fftBuffer[index + 1]).toDouble())

                //振幅が最大の周波数とその振幅値の解析
                var maxAmplitude = 0.0 // 最大振幅
                var maxIndex = 0 // 最大振幅が入っているリスト番号
                // 最大振幅が入っているリスト番号を捜査
                for(index in IntStream.range(0, fftBuffer.size - 1)){
                    val tmp = sqrt((fftBuffer[index] * fftBuffer[index] + fftBuffer[index + 1] * fftBuffer[index + 1]))
                    if (maxAmplitude < tmp){
                        maxAmplitude = tmp
                        maxIndex = index
                    }
                }
                // 最大振幅の周波数
                val maxFrequency: Double = (((maxIndex * sampleRate / fftBuffer.size)/2).toDouble())
                Log.d(LOG_NAME, "maxFrequency = $maxFrequency")

                val nearestEnum = findNearestEnumByFrequency(maxFrequency)


                if (nearestEnum != null) {
                    if(nowDB > 50){
                        detectSoundViewModel.setOnkai(nearestEnum.jpName)
                        detectSoundViewModel.setOnkaiEn(nearestEnum.name)
                    }
                    println("Nearest Enum: ${nearestEnum.jpName} (${nearestEnum.names}), Frequency: ${nearestEnum.frequency},Maxrequency = $maxFrequency")
                } else {
                    println("No matching enum found.")
                }


                // stop用のフラグ
                if (run) {
                    // 指定時間後に自分自身を呼ぶ
                    hnd0.postDelayed(this, period.toLong())
                }
            }
        }
        // 初回の呼び出し
        hnd0.post(rnb0)
    }



}

