package com.aslibayar.foody.ui.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.foody.ListingRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
        const val ERROR_MESSAGE = "Bir hata oluştu"
    }

    private val _uiState = MutableStateFlow(ListingUIState(screenType = route.screenType))
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<ListingEffect>()
    val effect = _effect.receiveAsFlow()

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

    fun onEvent(event: ListingEvent) {
        when (event) {
            is ListingEvent.ChangeScreenType -> handleScreenTypeChange(event.screenType)
            is ListingEvent.GetRecipes -> getRecipeList()
            is ListingEvent.OpenRecipeDetail -> navigateToDetail(event.recipeId)
            is ListingEvent.ToggleFavorite -> toggleFavorite(event.recipe)
        }
    }

    private fun handleScreenTypeChange(newScreenType: ScreenType) {
        _uiState.update { it.copy(screenType = newScreenType) }
        loadInitialData()
    }

    private fun navigateToDetail(recipeId: Int) {
        viewModelScope.launch {
            _effect.send(ListingEffect.NavigateToDetail(recipeId))
        }
    }

    private fun toggleFavorite(recipe: RecipeDetailUIModel) {
        viewModelScope.launch {
            try {
                if (repository.isFavorite(recipe.id)) {
                    repository.removeRecipeFromFavorite(recipe)
                } else {
                    repository.addRecipeToFavorite(recipe)
                }
            } catch (e: Exception) {
                _effect.send(ListingEffect.ShowError("Favori işlemi başarısız oldu"))
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteRecipes()
                .collect { favorites ->
                    _uiState.update {
                        it.copy(
                            recipes = favorites,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun observeRecentRecipes() {
        viewModelScope.launch {
            repository.getRecentRecipes()
                .collect { recents ->
                    _uiState.update {
                        it.copy(
                            recipes = recents,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes()
                .collect { result ->
                    when (result) {
                        is BaseUIModel.Loading -> handleLoading()
                        is BaseUIModel.Success -> handleSuccess(result.data)
                        is BaseUIModel.Error -> handleError()
                    }
                }
        }
    }

    private suspend fun handleSuccess(recipes: List<RecipeUIModel?>) {
        val recipesWithFavorites = recipes.map { recipe ->
            recipe?.copy(
                isFavorite = recipe.id.let { repository.isFavorite(it) }
            )
        }

        val filteredRecipes = when (route.screenType) {
            ScreenType.VEGAN -> recipesWithFavorites.filter { it?.vegan == true }
            ScreenType.GLUTEN_FREE -> recipesWithFavorites.filter { it?.glutenFree == true }
            else -> recipesWithFavorites
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                recipes = filteredRecipes
            )
        }
    }

    private fun handleLoading() {
        if (!_uiState.value.isRefreshing) {
            _uiState.update { it.copy(isLoading = true) }
        }
    }

    private suspend fun handleError() {
        _uiState.update { it.copy(isLoading = false) }
        _effect.send(ListingEffect.ShowError(ERROR_MESSAGE))
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isRefreshing = true) }
                repository.getRandomRecipes()
                    .collect { result ->
                        when (result) {
                            is BaseUIModel.Loading -> handleLoading()
                            is BaseUIModel.Success -> {
                                handleSuccess(result.data)
                                _uiState.update { it.copy(isRefreshing = false) }
                            }

                            is BaseUIModel.Error -> {
                                handleError()
                                _uiState.update { it.copy(isRefreshing = false) }
                            }
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}
