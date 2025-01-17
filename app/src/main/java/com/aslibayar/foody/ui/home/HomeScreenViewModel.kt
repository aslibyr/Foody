package com.aslibayar.foody.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.network.NetworkStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _recipeList = MutableStateFlow(HomeScreenUIStateModel())
    val recipeList = _recipeList.asStateFlow()

    private val _todaysSpecialRecipes = MutableStateFlow<List<RecipeUIModel>>(emptyList())
    val todaysSpecialRecipes = _todaysSpecialRecipes.asStateFlow()

    init {
        getRecipeList()
        getTodaysSpecialRecipes()
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        viewModelScope.launch {
            NetworkStateHolder.isConnected.collect { isConnected ->
                if (isConnected) {
                    retryFetchingData()
                }
            }
        }
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

    private fun getTodaysSpecialRecipes() {
        viewModelScope.launch {
            repository.getTodaysSpecialRecipes().collect { recipes ->
                _todaysSpecialRecipes.value = recipes
            }
        }
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            try {
                _recipeList.update { it.copy(isRefreshing = true) }
                repository.getRandomRecipes()
                    .collect { result ->
                        when (result) {
                            is BaseUIModel.Loading -> {
                                _recipeList.update { it.copy(isLoading = true) }
                            }

                            is BaseUIModel.Success -> {
                                _recipeList.update {
                                    it.copy(
                                        isRefreshing = false,
                                        recipes = result.data,
                                        isLoading = false,
                                    )
                                }
                            }

                            is BaseUIModel.Error -> {
                                _recipeList.update {
                                    it.copy(
                                        isRefreshing = false,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                _recipeList.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun retryFetchingData() {
        viewModelScope.launch {
            _recipeList.update { it.copy(isLoading = true, isRefreshing = false) }

            try {
                repository.getRandomRecipes().collect { result ->
                    when (result) {
                        is BaseUIModel.Loading -> {
                            _recipeList.update { it.copy(isLoading = true) }
                        }

                        is BaseUIModel.Success -> {
                            _recipeList.update {
                                it.copy(
                                    isLoading = false,
                                    recipes = result.data
                                )
                            }
                            // Today's special'ı da yenile
                            getTodaysSpecialRecipes()
                        }

                        is BaseUIModel.Error -> {
                            _recipeList.update { it.copy(isLoading = false) }
                        }
                    }
                }
            } catch (e: Exception) {
                _recipeList.update { it.copy(isLoading = false) }
            }
        }
    }
}