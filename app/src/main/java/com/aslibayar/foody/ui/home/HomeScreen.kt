package com.aslibayar.foody.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.foody.components.loading.CustomLoading
import com.aslibayar.foody.ui.home.components.quickaccess.QuickAccess
import com.aslibayar.foody.ui.home.components.widget.RecipeWidget
import com.aslibayar.foody.ui.home.components.widget.RecipeWidgetComponentModel
import com.aslibayar.foody.ui.listing.ScreenType
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    openRecipeDetailScreen: (recipeId: Int) -> Unit,
    onQuickAccessClick: (ScreenType) -> Unit,
) {
    val recipeList by viewModel.recipeList.collectAsStateWithLifecycle()
    val todaysSpecialRecipes by viewModel.todaysSpecialRecipes.collectAsStateWithLifecycle()

    if (recipeList.isLoading) {
        CustomLoading()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Today's Special widget
            RecipeWidget(
                model = RecipeWidgetComponentModel(
                    widgetCategory = "Today's Special",
                    recipes = todaysSpecialRecipes
                ),
                itemWidth = 280.dp,
                itemHeight = 200.dp,
                openRecipeDetailScreen = openRecipeDetailScreen,
                showViewAll = false // View All gizli
            )

            QuickAccess(onQuickAccessClick = onQuickAccessClick)

            RecipeWidget(
                model = RecipeWidgetComponentModel(
                    recipes = recipeList.recipes,
                    widgetCategory = "All Recipes"
                ),
                openListScreen = { onQuickAccessClick(ScreenType.TODAY) },
                openRecipeDetailScreen = openRecipeDetailScreen
            )
            RecipeWidget(
                model = RecipeWidgetComponentModel(
                    recipes = recipeList.recipes.shuffled().filter { it?.glutenFree ?: false },
                    widgetCategory = "Gluten Free"
                ),
                openListScreen = { onQuickAccessClick(ScreenType.GLUTEN_FREE) },
                openRecipeDetailScreen = openRecipeDetailScreen
            )
        }
    }
}