package com.aslibayar.foody.ui.ai

import com.aslibayar.network.response.RecipeAIResponse


data class AiChefUiState(
    val isLoading: Boolean = false,
    val recipe: RecipeAIResponse? = null,
    val messages: List<ChatMessage> = emptyList(),
    val error: String = ""
)

sealed class ChatMessage {
    data class UserMessage(val text: String) : ChatMessage()
    data class AiMessage(val recipe: RecipeAIResponse) : ChatMessage()
    data class ErrorMessage(val error: String) : ChatMessage()
}