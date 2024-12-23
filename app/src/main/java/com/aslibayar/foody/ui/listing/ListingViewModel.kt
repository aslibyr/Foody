package com.aslibayar.foody.ui.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.foody.ListingRoute
import com.aslibayar.foody.ui.home.HomeScreenUIStateModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListingViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    val screenType = savedStateHandle.toRoute<ListingRoute>().screenType

    private val _uiState = MutableStateFlow(HomeScreenUIStateModel())
    val uiState: StateFlow<HomeScreenUIStateModel> = _uiState.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes().collect { result ->
                when (result) {
                    is BaseUIModel.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is BaseUIModel.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            recipes = filterRecipes(result.data)
                        )
                    }

                    is BaseUIModel.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun filterRecipes(allRecipes: List<RecipeUIModel?>): List<RecipeUIModel> {
        return allRecipes.filterNotNull().filter { recipe ->
            when (screenType) {
                ScreenType.VEGAN -> recipe.vegan
                ScreenType.MEAT -> recipe.vegetarian.not()
                else -> true
            }
        }
    }
}
