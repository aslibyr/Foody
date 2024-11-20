package com.aslibayar.foody

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
object SecondRoute

@Serializable
data class RecipeDetailRoute(val recipeId: Int)