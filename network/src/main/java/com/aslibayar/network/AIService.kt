package com.aslibayar.network

import com.google.ai.client.generativeai.GenerativeModel

class GenAiService(
    private val textModel: GenerativeModel,
) {
    private val textChat = textModel.startChat()

    suspend fun suggestRecipe(ingredients: String): String {
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
            val response = textChat.sendMessage(prompt)
            response.text ?: "Sorry, I cannot suggest a recipe at the moment."
        } catch (e: Exception) {
            "An error occurred: ${e.message}"
        }
    }
}