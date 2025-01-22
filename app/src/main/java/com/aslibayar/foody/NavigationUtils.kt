package com.aslibayar.foody

import com.aslibayar.foody.ui.listing.ScreenType
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
object SearchRoute

@Serializable
object AIRoute

@Serializable
data class RecipeDetailRoute(val recipeId: Int)

@Serializable
data class ListingRoute(val screenType: ScreenType = ScreenType.TODAY)