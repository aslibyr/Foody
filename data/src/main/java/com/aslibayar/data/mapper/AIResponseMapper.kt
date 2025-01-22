package com.aslibayar.data.mapper

import android.util.Log
import com.aslibayar.network.response.RecipeAIResponse

object AIResponseMapper {
    fun mapAIResponseToRecipe(response: String): RecipeAIResponse {
        Log.d("GeminiDebug", "Mapper: Starting to parse response: $response")

        // Şimdilik tüm yanıtı recipeName'e koyalım
        return RecipeAIResponse(
            recipeName = response,
            ingredients = emptyList(),
            instructions = emptyList(),
            cookingTime = "",
            servings = ""
        ).also {
            Log.d("GeminiDebug", "Mapper: Raw response in recipe name: ${it.recipeName}")
        }
    }
}