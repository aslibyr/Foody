package com.aslibayar.foody.ui.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
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

    private val _uiState = MutableStateFlow(ListingUIState(screenType = route.screenType))
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<ListingEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onEvent(ListingEvent.GetRecipes)
    }

    fun onEvent(event: ListingEvent) {
        when (event) {
            is ListingEvent.ChangeScreenType -> {
                _uiState.update { it.copy(screenType = event.screenType) }
                onEvent(ListingEvent.GetRecipes)
            }

            is ListingEvent.GetRecipes -> {
                getRecipeList()
            }

            is ListingEvent.OpenRecipeDetail -> {
                viewModelScope.launch {
                    _effect.send(ListingEffect.NavigateToDetail(event.recipeId))
                }
            }
        }
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes().collect { result ->
                when (result) {
                    is BaseUIModel.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is BaseUIModel.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                recipes = filterRecipes(result.data)
                            )
                        }
                    }

                    is BaseUIModel.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _effect.send(ListingEffect.ShowError("Bir hata oluştu"))
                    }
                }
            }
        }
    }

    private fun filterRecipes(recipes: List<RecipeUIModel?>): List<RecipeUIModel?> {
        return when (uiState.value.screenType) {
            ScreenType.VEGAN -> recipes.filter { it?.vegan == true }
            ScreenType.GLUTEN_FREE -> recipes.filter { it?.glutenFree == true }
            else -> recipes
        }
    }
}
