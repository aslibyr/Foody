package com.aslibayar.data.model

data class RecipeUIModel(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    val glutenFree: Boolean = false,
    val readyInMinutes: String = "",
    val isFavorite: Boolean = false
)
