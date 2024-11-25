package com.aslibayar.data.model

data class RecipeUIModel(
    val id: Int,
    val title: String,
    val image: String,
)

data class RecipeDetailUIModel(
    val id: Int = 0,
    val title: String? = null,
    val image: String? = null,
    val summary: String? = null,
    val instructions: String? = null,
    val extendedIngredients: List<RecipeIngredientsUIModel> = emptyList()
)

data class RecipeIngredientsUIModel(
    val ingredientId: Int = 0,
    val image: String? = null,
    val name: String? = null
)
