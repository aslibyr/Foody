package com.aslibayar.foody.ui.search

import com.aslibayar.data.model.RecipeUIModel

data class SearchScreenUIStateModel(
    val recipes: List<RecipeUIModel?> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
)
