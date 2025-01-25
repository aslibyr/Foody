package com.aslibayar.data.mapper

import android.util.Log
import com.aslibayar.network.response.RecipeAIResponse

object AIResponseMapper {
    fun mapAIResponseToRecipe(response: String): RecipeAIResponse {
        Log.d("GeminiDebug", "Mapper: Starting to parse response: $response")

        val cleanedResponse = response.replace("**", "")

        return RecipeAIResponse(
            recipe = cleanedResponse,
        ).also {
            Log.d("GeminiDebug", "Mapper: Raw response in recipe name: ${it.recipe}")
        }
    }
}