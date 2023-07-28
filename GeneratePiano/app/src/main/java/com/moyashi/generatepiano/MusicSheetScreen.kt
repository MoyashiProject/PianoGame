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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moyashi.generatepiano.backgroundTask.GenerateScore
import com.moyashi.generatepiano.enum.enum_musicStep
import com.moyashi.generatepiano.function.ShibuOnpu
import java.util.Date

@Composable
fun MusicSheetScreen(practice:Practice?,viewModel:DetectSoundViewModel) {

    val counter = viewModel.counter.observeAsState()
    val onpuHeight = viewModel.onpu_height.observeAsState()
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Draw the line on the Canvas
        Canvas(
            modifier = Modifier.fillMaxSize()
//吉田
        ) {
            for (i in 0 until 5) {
                val y = (i + 1) * size.height / 6 // 6等分した位置に線を描画する
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 5f
                )
        Column(modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
        ){
            Box(){
                Column(modifier = Modifier){
                    Row(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth().padding(start = 150.dp),
                    ){
                        Gosenhu(practice)
                    }
                    Row(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth().padding(start = 150.dp),
                    ){
                        GosenhuShita(practice = practice)
                    }
                }
//                Row(modifier = Modifier.padding(start = 120.dp)){
//                    practice?.right_hand?.forEach{ hand ->
//                        Box(modifier = Modifier
//                            .padding(start = 44.dp)
//                            .offset(y = (-93).dp)) {
//                            setOnpu(name = "$hand")
//                        }
//                    }
//                }
            }
        }

        clef(drawable = "clef")
        clef(drawable = "bass_clef")

    }
}
@Composable
fun Gosenhu(practice: Practice?){
    Row{
        practice?.right_hand?.forEach{ right ->
            Box{
                val id = getEnumMusicID(right)
                Canvas(
                    modifier = Modifier
                        .width(50.dp)
                        .fillMaxHeight()
                        .padding(vertical = 32.dp)
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
                        if(i == 4){

                        }
                    }
                }
                if (id != null) {
                    SetShibuOnpu(id = id, position = 1)
                }
            }
        }
    }
}
@Composable
fun GosenhuShita(practice: Practice?){
    Row{
        practice?.right_hand?.forEach{ right ->
            Box{
                val id = getEnumMusicID(right)
                Canvas(
                    modifier = Modifier
                        .width(50.dp)
                        .fillMaxHeight()
                        .padding(vertical = 32.dp)
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
                        if(i == 4){

                        }
                    }
                }
                if (id != null) {
                    SetShibuOnpu(id = id, position = 2)
                }

            }
        }
    }
}
@Composable
fun SetShibuOnpu(id:Int,position:Int){
    if(position == 1){
        if(id >= 37){

            Box(modifier = Modifier.offset(y = (-5).times(id - 51).dp).fillMaxWidth()){
                Box(modifier = Modifier.offset(y = (90).dp)){
                    ShibuOnpu(id = id)

                }
                Text(text = "$id")
            }
        }
    } else{
        if(id < 37){
            Box(modifier = Modifier.offset(y = (-4.98).times(id - 51).dp).fillMaxWidth()){
                Box(modifier = Modifier.offset(y = (-15).dp)){
                    ShibuOnpu(id = id)

                }
                Text(text = "$id")
            }
        }

    }


}
fun getEnumMusicID(name: String): Int? {
    // enum_musicStepのvaluesをループして、nameと一致するものを探す
    for (value in enum_musicStep.values()) {
        // nameとvalueのnamesが一致したら、そのvalueのidを返す
        if (name == value.names) {
            return value.id
        }
    }
    // 一致するものがなかったら、nullを返す
    return 51
}

@Composable
fun clef(drawable: String){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ){
        if(drawable == "clef"){
            Image(
                painter = painterResource(id = R.drawable.clef),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(100.dp)
                    .offset(x = (-20).dp, y = (-90).dp)
            )
            ViewFraction(upText = 4, downText = 4, x = 80,y1= -115,y2 = -73)
        }else if(drawable == "bass_clef"){
            Image(
                painter = painterResource(id = R.drawable.bass_clef),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .offset(x = (-0).dp, y = (90).dp)
            )
            ViewFraction(upText = 4, downText = 4, x = 80,y1= 110,y2 = 68)
        }
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

