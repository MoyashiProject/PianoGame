package com.moyashi.generatepiano

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moyashi.generatepiano.backgroundTask.GenerateScore
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PracticeItem(
    practice: Practice,
    navController: NavHostController
) {
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                navController.navigate("${MainActivity.Route.THIRD.name}/${practice.id}")
            }
            .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
    ) {//title表示
        Text(
            text = practice.title,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 23.dp, start = 5.dp)
                .background(color = Color.Black)
        )
        Text( //作成日時表示
            text = "created at: ${sdf.format(practice.created_at)}",
            fontSize = 12.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
