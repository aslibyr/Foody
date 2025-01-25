package com.aslibayar.network

import com.google.ai.client.generativeai.GenerativeModel

class GenAiService(
    textModel: GenerativeModel,
) {
    private val textChat = textModel.startChat()

    suspend fun suggestRecipe(question: String): String {
        val basePrompt = when {
            question.equals("What should I cook today?", ignoreCase = true) -> {
                """
                You are a professional chef. Suggest a delicious recipe for today.
                Ask the user what type of cuisine or dish they prefer or what do they have in kitchen.
                Only English available.
                Format your response as:
                
                Recipe Name:
                Ingredients:
                Instructions:
                Cooking Time:
                Servings:
                """
            }

            else -> {
                """
                You are a professional chef. Help the user with their cooking question: $question
                If they're asking for a recipe, format your response as:
                
                Recipe Name:
                Ingredients:
                Instructions:
                Cooking Time:
                Servings:
                
                If it's a general cooking question, provide a clear and helpful answer.
                """
            }
        }.trimIndent()

        return try {
            val response = textChat.sendMessage(basePrompt)
            response.text ?: "Sorry, I cannot help with that at the moment."
        } catch (e: Exception) {
            "An error occurred: ${e.message}"
        }
    }
}