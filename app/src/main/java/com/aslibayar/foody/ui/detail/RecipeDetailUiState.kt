package com.aslibayar.foody.ui.detail

import com.aslibayar.data.model.RecipeDetailUIModel

sealed interface RecipeDetailUiState {
    data object Loading : RecipeDetailUiState
    data class Error(val message: String) : RecipeDetailUiState
    data class Success(
        val recipe: RecipeDetailUIModel,
        val isFavorite: Boolean = false
    ) : RecipeDetailUiState
}

sealed interface RecipeDetailEvent {
    data class ShowToast(val message: String) : RecipeDetailEvent
}