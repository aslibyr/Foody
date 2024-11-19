package com.aslibayar.foody

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aslibayar.foody.components.bottom_bar.BottomBar
import com.aslibayar.foody.components.navigation.MainNavigation
import com.aslibayar.foody.ui.theme.FoodyTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val showBottomBar by remember {
                derivedStateOf {
                    navBackStackEntry?.destination?.hasRoute<HomeScreenRoute>() == true ||
                            navBackStackEntry?.destination?.hasRoute<SecondRoute>() == true
                }
            }
            FoodyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomBar(
                        navController = navController,
                        isBottomBarVisible = showBottomBar
                    )
                }) { innerPadding ->
                    MainNavigation(
                        navController = navController, modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

