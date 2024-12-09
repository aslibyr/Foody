package com.aslibayar.foody.ui.listing

import com.aslibayar.data.model.RecipeUIModel

sealed class ListingUIEvents {

    data class ChangeScreenType(val screenType: ScreenType) : ListingUIEvents()
}

data class ListingState(
    val uiModel: RecipeUIModel = RecipeUIModel(),
    val screenType: ScreenType = ScreenType.VEGAN
)

enum class ScreenType {
    FAVORITE,
    RECENT,
    VEGAN,
    MEAT
}
