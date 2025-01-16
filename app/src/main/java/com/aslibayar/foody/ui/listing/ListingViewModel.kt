package com.aslibayar.foody.ui.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.foody.ListingRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListingViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    private val route = ListingRoute(
        screenType = savedStateHandle.get<ScreenType>("screenType") ?: ScreenType.TODAY
    )

    private companion object {
        const val ERROR_MESSAGE = "Error occured"
    }

    private val _uiState = MutableStateFlow(ListingUIState(screenType = route.screenType))
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        when (route.screenType) {
            ScreenType.FAVORITE -> observeFavorites()
            ScreenType.RECENT -> observeRecentRecipes()
            else -> getRecipeList()
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteRecipes()
                .collect { favorites ->
                    _uiState.update { it.copy(recipes = favorites, isLoading = false) }
                }
        }
    }

    private fun observeRecentRecipes() {
        viewModelScope.launch {
            repository.getRecentRecipes()
                .collect { recents ->
                    _uiState.update { it.copy(recipes = recents, isLoading = false) }
                }
        }
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes()
                .collect { result ->
                    when (result) {
                        is BaseUIModel.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is BaseUIModel.Success -> {
                            val recipesWithFavorites = result.data.map { recipe ->
                                recipe?.copy(isFavorite = repository.isFavorite(recipe.id))
                            }
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    recipes = recipesWithFavorites
                                )
                            }
                        }

                        is BaseUIModel.Error -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = ERROR_MESSAGE
                            )
                        }
                    }
                }
        }
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isRefreshing = true) }
                repository.getRandomRecipes()
                    .collect { result ->
                        when (result) {
                            is BaseUIModel.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is BaseUIModel.Success -> {
                                getRecipeList()
                                _uiState.update { it.copy(isRefreshing = false) }
                            }

                            is BaseUIModel.Error -> {
                                _uiState.update { it.copy(isRefreshing = false, isLoading = false) }
                            }
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}
