package com.aslibayar.data.model

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
    val servings: String,
    val isFavorite: Boolean = false,
    val analyzedInstructions: List<AnalyzedInstruction>
)

data class RecipeIngredientsUIModel(
    val ingredientId: Int,
    val image: String,
    val name: String,
    val original: String,
)

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
)

data class Ingredient(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)

data class Equipment(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)