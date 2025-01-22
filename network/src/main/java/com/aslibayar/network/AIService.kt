package com.aslibayar.network

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel

class GenAiService(
    textModel: GenerativeModel,
) {
    private val textChat = textModel.startChat()

    suspend fun suggestRecipe(ingredients: String): String {
        Log.d("GeminiDebug", "GenAiService: suggestRecipe called with ingredients: $ingredients")
        
        val prompt = """
            You are a professional chef. I have these ingredients: $ingredients
            What's the best recipe I can make with these ingredients?
            
            Please detect the language of my input and respond in the same language.
            Use this format in the detected language:
            
            Recipe Name:
            Ingredients:
            Instructions:
            Cooking Time:
            Servings:
            
            Only use the ingredients I mentioned, don't suggest additional ingredients.
        """.trimIndent()

        return try {
            Log.d("GeminiDebug", "GenAiService: Sending prompt to Gemini")
            val response = textChat.sendMessage(prompt)
            Log.d("GeminiDebug", "GenAiService: Received response: ${response.text}")
            response.text ?: "Sorry, I cannot suggest a recipe at the moment."
        } catch (e: Exception) {
            Log.e("GeminiDebug", "GenAiService: Error", e)
            "An error occurred: ${e.message}"
        }
    }
}