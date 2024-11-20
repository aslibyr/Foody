package com.aslibayar.foody.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.data.model.BaseUIModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    modifier: Modifier,
    openRecipeDetailScreen: (recipeId: Int) -> Unit
) {
    val recipeList = viewModel.recipeList.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        when (val recipes = recipeList.value) {
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                recipes.data.forEach {
                    Text(text = it?.title ?: "", modifier = modifier.clickable {
                        it?.id?.let { recipeId ->
                            openRecipeDetailScreen(recipeId)
                        }
                    })
                }
            }
        }
    }
}
