package com.aslibayar.foody.ui.listing

import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel

// UI State
data class ListingUIState(
    val recipes: List<RecipeUIModel?> = emptyList(),
    val isLoading: Boolean = false,
    val screenType: ScreenType = ScreenType.TODAY,
    val favMovies: List<RecipeUIModel> = emptyList(),
    val isRemoved: Boolean = false
)

// UI Events
sealed interface ListingEvent {
    data class ChangeScreenType(val screenType: ScreenType) : ListingEvent
    data object GetRecipes : ListingEvent
    data class OpenRecipeDetail(val recipeId: Int) : ListingEvent
    data class ToggleFavorite(val recipe: RecipeDetailUIModel) : ListingEvent
}

// UI Effects (one-time events)
sealed interface ListingEffect {
    data class ShowError(val message: String) : ListingEffect
    data class NavigateToDetail(val recipeId: Int) : ListingEffect
}

enum class ScreenType(val widgetType: String) {
    FAVORITE("Favorite"),
    RECENT("Recent"),
    VEGAN("Vegan"),
    GLUTEN_FREE("Gluten Free"),
    TODAY("Today's Special")
}