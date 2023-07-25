package com.moyashi.generatepiano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moyashi.generatepiano.ui.theme.GeneratePianoTheme

class MainActivity : ComponentActivity() {
    private val viewModel = PracticeViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppScreen(viewModel)
        }
    }

    enum class Route { // ナビゲーションルートの定義
        FIRST,
        SECOND,
        THIRD,
        FOURTH;
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyAppScreen(viewModel: PracticeViewModel) {
        val navController = rememberNavController()

        GeneratePianoTheme {
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = Route.FIRST.name, //最初の画面をMainScreenに設定
                    modifier = Modifier.padding(it)
                ) {
                    //画面1
                    composable(route = Route.FIRST.name) {
                        MainScreen(viewModel, navController)
                    }
                    //画面２
                    composable(route = Route.SECOND.name) {
                        //よっしーの作成した画面を設定する
                    }
                    // 画面3
                    composable(
                        route = "${Route.THIRD.name}/{practiceId}",
                        arguments = listOf(navArgument("practiceId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val practiceId = backStackEntry.arguments?.getLong("practiceId")
                        val practice = viewModel.retrievePracticeById(practiceId ?: 0L)
                        MusicDetailScreen(practice, navController,viewModel)
                    }
                    composable(
                        route = Route.FOURTH.name
                    ) {

                    }
                }
            }
        }
    }
}