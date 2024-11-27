package com.aslibayar.foody.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipeList =
        MutableStateFlow(SearchScreenUIStateModel())
    val recipeList = _recipeList.asStateFlow()

    fun searchRecipe(query: String) {
        _recipeList.update { it.copy(isLoading = true, searchQuery = query) }

        viewModelScope.launch(Dispatchers.IO) {
            repository.searchRecipe(query).collect { result ->
                when (result) {
                    is BaseUIModel.Loading -> {
                        _recipeList.update { it.copy(isLoading = true) }
                    }

                    is BaseUIModel.Success -> {
                        _recipeList.update {
                            it.copy(
                                recipes = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is BaseUIModel.Error -> {
                        _recipeList.update {
                            it.copy(
                                recipes = emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
