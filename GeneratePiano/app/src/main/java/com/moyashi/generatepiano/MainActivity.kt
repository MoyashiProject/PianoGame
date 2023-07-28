package com.moyashi.generatepiano

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moyashi.generatepiano.backgroundTask.CollectSoundStream
import com.moyashi.generatepiano.ui.theme.GeneratePianoTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        val viewModel = PracticeViewModel()
        val detectSoundViewModel = ViewModelProvider(this)[DetectSoundViewModel::class.java]
        val sound = CollectSoundStream(context,detectSoundViewModel)
        sound.start(100)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyAppScreen(viewModel,detectSoundViewModel)
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
    fun MyAppScreen(viewModel: PracticeViewModel,detectSoundViewModel: DetectSoundViewModel) {
        val navController = rememberNavController()

        GeneratePianoTheme {
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = Route.FIRST.name, //最初の画面をMainScreenに設定
                    modifier = Modifier.padding(it)
                ) {
                    //練習曲一覧
                    composable(route = Route.FIRST.name) {
                        MainScreen(viewModel, navController)
                    }
                    //画面２
                    composable(
                        route = "${Route.SECOND.name}/{practiceId}",
                        arguments = listOf(navArgument("practiceId"){type= NavType.LongType})
                    ) {backStackEntry ->
                        //楽譜表示画面
                        val practiceID = backStackEntry.arguments?.getLong("practiceId")
                        val practice = viewModel.retrievePracticeById(practiceID ?:0L)
                        MusicSheetScreen(practice,detectSoundViewModel)
                    }
                    // 詳細画面
                    composable(
                        route = "${Route.THIRD.name}/{practiceId}",
                        arguments = listOf(navArgument("practiceId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val practiceId = backStackEntry.arguments?.getLong("practiceId")
                        val practice = viewModel.retrievePracticeById(practiceId ?: 0L)
                        MusicDetailScreen(practice, navController, viewModel)
                    }
                    composable(
                        route = Route.FOURTH.name
                    ) {
                        //楽譜作成画面
                    }
                }
            }
        }
    }
}

