package com.aslibayar.foody.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _recipeList =
        MutableStateFlow(HomeScreenUIStateModel())
    val recipeList = _recipeList.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes().collect {
                _recipeList.value = _recipeList.value.copy(
                    isLoading = it is BaseUIModel.Loading
                )
                if (it is BaseUIModel.Success) {
                    _recipeList.value = _recipeList.value.copy(
                        recipes = it.data
                    )
                }
            }
        }
    }
}