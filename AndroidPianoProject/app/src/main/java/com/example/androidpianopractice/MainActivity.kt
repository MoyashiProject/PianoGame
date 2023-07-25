package com.example.androidpianopractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.updateLayoutParams

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawLineExample()
        }
    }
}

@Composable
fun DrawLineExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Draw the line on the Canvas
        DrawLineCanvas(Modifier.fillMaxSize())
    }
}

@Composable
fun DrawLineCanvas(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        drawLine(
            color = Color.Blue,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = 5f // 線の幅を指定
        )
    }
}

