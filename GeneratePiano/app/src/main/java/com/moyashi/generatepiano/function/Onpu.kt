package com.moyashi.generatepiano.function

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ShibuOnpu(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Draw the ellipse (similar to the previous code)
        rotate(degrees = -30F) {
            scale(scaleX = 0.65f, scaleY = 6.4f.dp.toPx() / 22.1.dp.toPx()) {
                drawCircle(Color.Black, radius = 22.1.dp.toPx())
            }
        }

        // Calculate the starting and ending points for the vertical line
        val centerX = size.width / 2
        val centerY = size.height / 2
        val lineStart = Offset(centerX + 10.dp.toPx(), centerY - 70.dp.toPx())
        val lineEnd = Offset(centerX + 10.dp.toPx(), centerY)

        // Draw the vertical line
        drawLine(
            color = Color.Black,
            start = lineStart,
            end = lineEnd,
            strokeWidth = 3.dp.toPx()
        )
    }
}
@Preview
@Composable
fun View(){
    ShibuOnpu()
}