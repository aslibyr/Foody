package com.aslibayar.data.repository

import AIResponseMapper
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.network.GenAiService
import com.aslibayar.network.response.RecipeAIResponse

//interface AIRepository {
//    suspend fun suggestRecipe(ingredients: String): BaseUIModel<RecipeAIResponse>
//}

class AIRepositoryImpl(
    private val aiService: GenAiService
) {
    suspend fun suggestRecipe(ingredients: String): BaseUIModel<RecipeAIResponse> {
        return try {
            val response = aiService.suggestRecipe(ingredients)
            val recipe = AIResponseMapper.mapAIResponseToRecipe(response)
            BaseUIModel.Success(recipe)
        } catch (e: Exception) {
            BaseUIModel.Error(e.message ?: "An error occurred")
        }
    }
}