package com.aslibayar.foody.components.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aslibayar.foody.HomeScreenRoute
import com.aslibayar.foody.ListingRoute
import com.aslibayar.foody.RecipeDetailRoute
import com.aslibayar.foody.SearchRoute
import com.aslibayar.foody.ui.detail.RecipeDetailScreen
import com.aslibayar.foody.ui.home.HomeScreen
import com.aslibayar.foody.ui.listing.ListingScreen
import com.aslibayar.foody.ui.search.SearchScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
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
            HomeScreen(openRecipeDetailScreen = {
                val route = RecipeDetailRoute(it)
                navController.navigate(route)
            },
                onQuickAccessClick = {
                    val route = ListingRoute(it)
                    navController.navigate(route)
                }
            )
        }


        composable<SearchRoute> {
            SearchScreen(openRecipeDetailScreen = {
                val route = RecipeDetailRoute(it)
                navController.navigate(route)
            })
        }

        composable<ListingRoute> {
            ListingScreen()
        }

        composable<RecipeDetailRoute> {
            RecipeDetailScreen(onBackClick = {
                navController.popBackStack()
            })
        }
        }
    }
