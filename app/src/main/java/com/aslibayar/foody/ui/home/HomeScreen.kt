package com.aslibayar.foody.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel(), modifier: Modifier) {
    val recipeList = viewModel.recipeList.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val recipes = recipeList.value) {
            is com.aslibayar.network.ResultWrapper.GenericError -> {}
            is com.aslibayar.network.ResultWrapper.Success -> {
                recipes.value.recipes?.forEach {
                    it?.title?.let { it1 -> Text(text = it1) }
                }
            }
        }
    }
}