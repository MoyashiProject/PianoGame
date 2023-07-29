package com.moyashi.generatepiano

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat

@Composable
fun PracticeItem(
    practice: Practice,
    navController: NavHostController
) {
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                navController.navigate("${MainActivity.Route.THIRD.name}/${practice.id}")
            }
            .background(color = Color.White)
    ) {//title表示
        Text(
            text = "",
            modifier = Modifier
                .size(width = 200.dp, height = 24.dp)
                .background(color = Color.Black)
        )
        Text(
            text = practice.title,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 23.dp, start = 5.dp)
        )
        Text( //作成日時表示
            text = "created at: ${sdf.format(practice.created_at)}",
            fontSize = 12.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "",
            modifier = Modifier
                .size(width = 85.dp, height = 12.dp)
                .background(color = Color.Black)
                .padding(top = 58.dp, start = 0.dp)
        )
    }
}
