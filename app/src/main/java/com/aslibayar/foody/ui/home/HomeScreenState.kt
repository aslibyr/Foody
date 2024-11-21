package com.aslibayar.foody.ui.home

import com.aslibayar.data.model.RecipeUIModel

data class HomeScreenUIStateModel(
    val recipes: List<RecipeUIModel?> = emptyList(),
    val isLoading: Boolean = false,
)
