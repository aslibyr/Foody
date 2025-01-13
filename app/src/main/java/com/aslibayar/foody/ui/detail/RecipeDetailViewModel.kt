package com.aslibayar.foody.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.foody.RecipeDetailRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {
    private val recipeId = savedStateHandle.toRoute<RecipeDetailRoute>().recipeId

    private val _uiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RecipeDetailEvent>()
    val event = _event.asSharedFlow()

    init {
        getRecipeDetail(recipeId)
    }

    private fun getRecipeDetail(recipeId: Int) {
        viewModelScope.launch {
            repository.getRecipeDetail(recipeId).collect { result ->
                when (result) {
                    is BaseUIModel.Loading -> {
                        _uiState.value = RecipeDetailUiState.Loading
                    }

                    is BaseUIModel.Success -> {
                        // Detay görüntülendiğinde recent'a ekle
                        repository.addToRecentRecipes(result.data)
                        
                        val isFavorite = repository.isFavorite(recipeId)
                        _uiState.value = RecipeDetailUiState.Success(
                            recipe = result.data,
                            isFavorite = isFavorite
                        )
                    }
                    is BaseUIModel.Error -> {
                        _uiState.value = RecipeDetailUiState.Error(
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value as? RecipeDetailUiState.Success ?: return

        viewModelScope.launch {
            try {
                handleFavoriteStatus(currentState)
            } catch (e: Exception) {
                _uiState.value =
                    RecipeDetailUiState.Error("Favori işlemi başarısız oldu: ${e.message}")
            }
        }
    }

    private suspend fun handleFavoriteStatus(currentState: RecipeDetailUiState.Success) {
        withContext(Dispatchers.IO) {
            try {
                with(currentState) {
                    if (isFavorite) {
                        repository.removeRecipeFromFavorite(recipe)
                        emitToastMessage("${recipe.title} removed from favorites")
                    } else {
                        repository.addRecipeToFavorite(recipe)
                        emitToastMessage("${recipe.title} added to favorites")
                    }

                    withContext(Dispatchers.Main) {
                        _uiState.value = copy(
                            isFavorite = !isFavorite
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value =
                        RecipeDetailUiState.Error("Favorite operation failed: ${e.message}")
                    emitToastMessage("Failed to update favorites")
                }
            }
        }
    }

    private suspend fun emitToastMessage(message: String) {
        withContext(Dispatchers.Main) {
            _event.emit(RecipeDetailEvent.ShowToast(message))
        }
    }
}