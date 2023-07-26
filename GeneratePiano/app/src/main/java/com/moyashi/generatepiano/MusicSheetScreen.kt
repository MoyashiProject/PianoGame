package com.moyashi.generatepiano

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import com.moyashi.generatepiano.enum.enum_musicStep
import com.moyashi.generatepiano.function.ShibuOnpu
import java.util.Date

@Composable
fun MusicSheetScreen(practice:Practice?,viewModel:DetectSoundViewModel) {

    val counter = viewModel.counter.observeAsState()

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Box(
        modifier = Modifier.fillMaxSize().background(Color(red = 234, green = 65,blue=39))
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
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
                                .padding(vertical = 32.dp)
                                .background(Color(red = 234, green = 65,blue=39))
                        ) {
                            for (i in 0 .. 4) {
                                val y = ( i + 1 ) * size.height / 6 // 6等分した位置に線を描画する
                                drawLine(
                                    color = Color.White,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = 5f
                                )
                            }
                        }
                        Row(modifier = Modifier.padding(start = 110.dp)){
                            practice?.right_hand?.forEach{ hand ->
                                Box(modifier = Modifier.padding(start = 45.dp)){
                                    setOnpu(name = "${hand}")
                                }
                            }
                        }
                    }


                }

            }
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
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
                                .padding(vertical = 32.dp)
                                .background(Color(red = 234, green = 65,blue=39))
                        ) {
                            for (i in 0 .. 4) {
                                val y = ( i + 1 ) * size.height / 6 // 6等分した位置に線を描画する
                                drawLine(
                                    color = Color.White,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = 5f
                                )
                            }
                        }
                        Row(modifier = Modifier.padding(start = 110.dp)){
                            practice?.right_hand?.forEach{ hand ->
                                Box(modifier = Modifier.padding(start = 45.dp)){
                                    setOnpu(name = "${hand}")
                                }
                            }
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
                    .height(200.dp)
                    .width(100.dp)
                    .offset(x = (-20).dp,y = (-90).dp)
            )
            ViewFraction(upText = 4, downText = 4, x = 80,y1= -115,y2 = -73)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Image(
                painter = painterResource(id = R.drawable.bass_clef),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .offset(x = (-0).dp,y = (90).dp)
            )
            ViewFraction(upText = 4, downText = 4, x = 80,y1= 110,y2 = 68)
        }
        counter.value?.let { count ->
            Text("$count", textAlign = TextAlign.Center,modifier = Modifier.width(150.dp), fontSize = 50.sp)
        }
    }

}
@Composable
fun setOnpu(name: String){
//    BoxWithConstraints {
//        val screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
//        val screenHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }
//
//    }
    for (pre in enum_musicStep.values()){
        if(pre.names.equals(name)){
            if(pre.id > 51){
                //音階が真ん中シ（シ３)よりも上だった場合のプログラム
                Box(modifier = Modifier.offset(y = (-7.5).times(pre.id-51).dp)){
                    ShibuOnpu()
                    Text("${pre.jpName}")
                }
            }else{
                Box(modifier = Modifier.offset(y = (17.3).times(51-pre.id).dp)){
                    ShibuOnpu()
                }
            }
        }
    }
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
fun ViewFraction(upText: Int, downText: Int,x: Int, y1: Int, y2: Int){
    val density = LocalDensity.current
    Text(
        text = "${upText}",
        modifier = Modifier
            .offset(x = x.dp, y = (y1).dp),
        fontSize = with(density) {60.dp.toSp()}
    )
    Text(
        text = "${downText}",
        modifier = Modifier
            .offset(x = x.dp, y = y2.dp),
        fontSize = with(density) {60.dp.toSp()}
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

