package com.aslibayar.foody.components.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aslibayar.foody.HomeScreenRoute
import com.aslibayar.foody.RecipeDetailRoute
import com.aslibayar.foody.SecondRoute
import com.aslibayar.foody.ui.SecondScreen
import com.aslibayar.foody.ui.detail.RecipeDetailScreen
import com.aslibayar.foody.ui.home.HomeScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }

    ) {
        composable<HomeScreenRoute> {
            HomeScreen(modifier = modifier, openRecipeDetailScreen = {
                val route = RecipeDetailRoute(it)
                navController.navigate(route)
            }
            )
        }


        composable<SecondRoute> {
            SecondScreen(modifier = modifier)
        }

        composable<RecipeDetailRoute> {
            RecipeDetailScreen(modifier = modifier)
        }
    }
}