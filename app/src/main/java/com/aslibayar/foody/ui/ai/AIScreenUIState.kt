package com.aslibayar.foody.ui.ai

import com.aslibayar.network.response.RecipeAIResponse


data class AiChefUiState(
    val isLoading: Boolean = false,
    val recipe: RecipeAIResponse? = null,
    val error: String = ""
)