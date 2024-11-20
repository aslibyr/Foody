package com.aslibayar.foody.ui.detail

import com.aslibayar.data.model.RecipeDetailUIModel

data class RecipeDetailUIStateModel(
    val recipe: RecipeDetailUIModel = RecipeDetailUIModel(),
    val recipeId: Int? = null,
    val isLoading: Boolean = false
)