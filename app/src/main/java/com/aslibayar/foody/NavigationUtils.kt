package com.aslibayar.foody

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
object SearchRoute

@Serializable
data class RecipeDetailRoute(val recipeId: Int)