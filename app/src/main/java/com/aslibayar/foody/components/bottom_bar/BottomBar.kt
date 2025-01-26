package com.aslibayar.foody.components.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alican.multimodulemovies.components.bottom_bar.BottomBarRoute
import com.aslibayar.foody.AIChefRoute
import com.aslibayar.foody.HomeScreenRoute
import com.aslibayar.foody.R
import com.aslibayar.foody.SearchRoute
import com.aslibayar.foody.ui.theme.Orange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BottomBar(
    navController: NavController,
    isBottomBarVisible: Boolean
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        BottomBarRoute(
            name = "Home",
            route = HomeScreenRoute,
            icon = ImageVector.vectorResource(R.drawable.food)
        ),
        BottomBarRoute(
            name = "Search",
            route = SearchRoute,
            icon = ImageVector.vectorResource(R.drawable.search)
        ),
        BottomBarRoute(
            name = "AI Chef",
            route = AIChefRoute,
            icon = ImageVector.vectorResource(R.drawable.chef)
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.height(110.dp)
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontSize = 10.sp,
                        )
                    },
                    icon = {
                        Icon(item.icon, contentDescription = item.name, Modifier.size(30.dp))
                    },
                    interactionSource = NoRippleInteractionSource,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Orange,
                        selectedTextColor = Orange,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    }
}

private object NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}