package com.moyashi.generatepiano

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moyashi.generatepiano.backgroundTask.GenerateScore
import com.moyashi.generatepiano.function.ShibuOnpu
import java.util.Date

@Composable
fun MusicSheetScreen(practice:Practice?,viewModel:DetectSoundViewModel) {

    val counter = viewModel.counter.observeAsState()

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .width(800.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                contentAlignment = Alignment.Center
            ){
                Box{
                    Canvas(
                        modifier = Modifier
                            .width(((practice?.right_hand?.size)?.times(67))!!.dp)
                            .height(500.dp)
                            .padding(vertical = 64.dp)
                            .background(Color.White)
                    ) {
                        for (i in 0 .. 4) {
                            val y = ( i + 1 ) * size.height / 6 // 6等分した位置に線を描画する
                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 5f
                            )
                        }
                    }

                }
                Row(modifier = Modifier.padding(start = 110.dp)){
                    practice?.right_hand?.forEach{ hand ->
                        Text(text="${hand}")
                        Box(modifier = Modifier.padding(start = 45.dp)){
                            ShibuOnpu()
                        }
                    }
                }

            }

        }
        // Draw the line on the Canvas

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Image(
                painter = painterResource(id = R.drawable.clef),
                contentDescription = null,
                modifier = Modifier
                    .height(400.dp)
                    .width(200.dp)
                    .offset(x = (-30).dp)
            )
            ViewFraction(upText = 4, downText = 4)
        }
        counter.value?.let { count ->
            Text("$count", textAlign = TextAlign.Center,modifier = Modifier.width(150.dp), fontSize = 50.sp)
        }
    }

}
@Composable
fun setOnpu(name: String){

}

@Preview
@Composable
fun View(){
    val scores = GenerateScore().GenerateEasy()
    val detectSoundViewModel = DetectSoundViewModel()
    Box(modifier = Modifier
        .width(1600.dp)
        .height(900.dp)){
        MusicSheetScreen(practice = Practice(1, "aaaa", created_at = Date(),scores,scores), viewModel = detectSoundViewModel)

    }
}

@Composable
fun ViewFraction(upText: Int, downText: Int){
    val density = LocalDensity.current
    Text(
        text = "${upText}",
        modifier = Modifier
            .offset(x = 120.dp, y = (-40).dp),
        fontSize = with(density) {100.dp.toSp()}
    )
    Text(
        text = "${downText}",
        modifier = Modifier
            .offset(x = 120.dp, y = 32.dp),
        fontSize = with(density) {100.dp.toSp()}
    )
}



@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose { }
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

