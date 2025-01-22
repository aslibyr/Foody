package com.aslibayar.network.response

data class RecipeAIResponse(
    val recipeName: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val cookingTime: String = "",
    val servings: String = ""
) 