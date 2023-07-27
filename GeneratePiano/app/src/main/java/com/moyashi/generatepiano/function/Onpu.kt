package com.moyashi.generatepiano.function

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ShibuOnpu(id:Int){

    val ellipseWidth = 22.5.dp
    val ellipseHeight = 14.dp
    val lineWidth = 2.dp
    val lineLength = 50.dp
    Canvas(
        modifier = Modifier
            .width(40.dp)
    ) {
        // 楕円を描画する
        rotate(degrees = (-20F)){
            drawOval(
                color = Color.Black,
                topLeft = Offset(
                    x = (size.width - ellipseWidth.toPx()) / 2,
                    y = (size.height - ellipseHeight.toPx()) / 2
                ),
                size = Size(ellipseWidth.toPx(), ellipseHeight.toPx())
            )
        }


        // 黒い線を描画する
        drawLine(
            color = Color.Black,
            start = Offset(
                x = (size.width + ellipseWidth.toPx()) / 2,
                y = (size.height - ellipseHeight.toPx()) / 2
            ),
            end = Offset(
                x = (size.width + ellipseWidth.toPx()) / 2,
                y = (size.height - ellipseHeight.toPx()) / 2 - lineLength.toPx()
            ),
            strokeWidth = lineWidth.toPx()
        )
        if(id == 40){
            drawLine(
                color = Color.Black,
                start = Offset(
                    x = (0f),
                    y = (size.height) / 2
                ),
                end = Offset(
                    x = size.width,
                    y = (size.height) / 2
                ),
                strokeWidth = 5F
            )
        }

    }
}
@Preview
@Composable
fun View(){
    ShibuOnpu(id = 40)
}