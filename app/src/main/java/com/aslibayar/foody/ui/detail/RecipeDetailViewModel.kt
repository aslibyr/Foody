package com.aslibayar.foody.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.foody.RecipeDetailRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {
    private val recipeId = savedStateHandle.toRoute<RecipeDetailRoute>().recipeId

    private val _uiState = MutableStateFlow(RecipeDetailUIStateModel())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        RecipeDetailUIStateModel()
    )

    init {
        getRecipeDetail(recipeId = recipeId)
    }

    private fun getRecipeDetail(recipeId: Int) {
        viewModelScope.launch {
            repository.getRecipeDetail(recipeId).collect {
                _uiState.value = _uiState.value.copy(
                    isLoading = it is BaseUIModel.Loading
                )
                if (it is BaseUIModel.Success) {
                    _uiState.value = _uiState.value.copy(
                        recipe = it.data
                    )
                }
            }
        }
    }
}