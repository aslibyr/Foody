package com.aslibayar.data.repository

import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.network.response.RecipeAIResponse

interface AIRepository {
    suspend fun suggestRecipe(ingredients: String): BaseUIModel<RecipeAIResponse>
}