package com.aslibayar.foody.ui.ai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.foody.components.loading.CustomLoading
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.LightOrange
import com.aslibayar.foody.ui.theme.Orange
import com.aslibayar.network.response.RecipeAIResponse
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiChefScreen(
    viewModel: AiChefViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    var userQuestion by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(
                index = uiState.messages.size,
                scrollOffset = 0
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart // Box içeriğini alta hizala
        ) {
            // Chat area
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                items(uiState.messages) { message ->
                    when (message) {
                        is ChatMessage.UserMessage -> {
                            UserMessageCard(message = message.text)
                        }

                        is ChatMessage.AiMessage -> {
                            RecipeCard(recipe = message.recipe)
                        }

                        is ChatMessage.ErrorMessage -> {
                            ErrorMessage(error = message.error)
                        }
                    }
                }

                if (uiState.isLoading) {
                    item {
                        CustomLoading()
                    }
                }
            }
        }
        // Input area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .imeNestedScroll()
        ) {
            Button(
                onClick = {
                    viewModel.suggestRecipe("What should I cook today?")
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                ),
                border = BorderStroke(1.dp, LightOrange),
            ) {
                Text(
                    "What should I cook today?",
                    style = CustomTextStyle.regularBlackMedium,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = userQuestion,
                    onValueChange = { userQuestion = it },
                    placeholder = {
                        Text(
                            "Ask your cooking question...",
                            style = CustomTextStyle.regularBlackMedium
                        )
                    },
                    modifier = Modifier.weight(1f),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(30.dp)
                )

                IconButton(
                    onClick = {
                        if (userQuestion.isNotBlank()) {
                            viewModel.suggestRecipe(userQuestion)
                            userQuestion = ""
                        }
                    },
                    enabled = userQuestion.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (userQuestion.isNotBlank()) Orange else Orange.copy(alpha = 0.38f)
                    )
                }
            }
        }
    }
}

@Composable
fun UserMessageCard(message: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 340.dp)
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Orange.copy(alpha = 0.1f)
            )
        ) {
            Text(
                text = message,
                style = CustomTextStyle.regularBlackMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun ErrorMessage(
    error: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {
        Text(
            text = error,
            style = CustomTextStyle.regularBlackSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RecipeCard(
    recipe: RecipeAIResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = recipe.recipe,
                style = CustomTextStyle.regularBlackMedium,
                color = Color.Black
            )
        }
    }
}