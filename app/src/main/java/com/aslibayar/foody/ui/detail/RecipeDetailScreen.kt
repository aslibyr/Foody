package com.aslibayar.foody.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Column(modifier = modifier.fillMaxSize()) {
        uiState.recipe.title?.let { Text(it) }
        uiState.recipe.image?.let { AsyncImage(it, "") }
    }
}