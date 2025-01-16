package com.aslibayar.foody.ui.listing

import com.aslibayar.data.model.RecipeUIModel

// UI State
data class ListingUIState(
    val recipes: List<RecipeUIModel?> = emptyList(),
    val isLoading: Boolean = false,
    val screenType: ScreenType = ScreenType.TODAY,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)


enum class ScreenType(val widgetType: String) {
    FAVORITE("Favorite"),
    RECENT("Recent"),
    VEGAN("Vegan"),
    GLUTEN_FREE("Gluten Free"),
    TODAY("Today's Special"),
    ALL("All Recipes")
}