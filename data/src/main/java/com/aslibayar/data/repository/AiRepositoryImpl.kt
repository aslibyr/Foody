package com.aslibayar.data.repository

import com.aslibayar.data.mapper.AIResponseMapper
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.network.GenAiService
import com.aslibayar.network.response.RecipeAIResponse

class AIRepositoryImpl(
    private val aiService: GenAiService
) : AIRepository {
    override suspend fun suggestRecipe(ingredients: String): BaseUIModel<RecipeAIResponse> {
        return try {
            val response = aiService.suggestRecipe(ingredients)
            val mappedResponse = AIResponseMapper.mapAIResponseToRecipe(response)
            BaseUIModel.Success(mappedResponse)
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("An internal error has occurred") == true ->
                    "AI service is currently not responding. Please try again later."

                e.message?.contains("MissingFieldException") == true ->
                    "AI service is experiencing a temporary issue. Please try again later."

                else -> "An error occurred. Please try again."
            }
            BaseUIModel.Error(errorMessage)
        }
    }
}