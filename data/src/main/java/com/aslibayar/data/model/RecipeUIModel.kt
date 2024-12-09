package com.aslibayar.data.model

data class RecipeUIModel(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
)

data class RecipeDetailUIModel(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val instructions: String,
    val extendedIngredients: List<RecipeIngredientsUIModel> = emptyList(),
    val sourceUrl: String,
    val diets: List<String> = emptyList(),
    val time: String,
    val servings: String
)

data class RecipeIngredientsUIModel(
    val ingredientId: Int,
    val image: String,
    val name: String
)
