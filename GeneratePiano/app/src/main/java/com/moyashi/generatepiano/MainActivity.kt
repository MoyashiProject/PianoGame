package com.moyashi.generatepiano

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
        THIRD;
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
                        MainScreen(viewModel)
                    }
                    //画面２
                    composable(route = Route.SECOND.name) {
                        //よっしーの作成した画面を設定する
                    }
                }
            }
        }
    }
}

