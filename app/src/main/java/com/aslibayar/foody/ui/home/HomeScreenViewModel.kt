package com.aslibayar.foody.ui.home

import RandomRecipeResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.network.RecipesApiService
import com.aslibayar.network.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val apiService: RecipesApiService) : ViewModel() {

    private val _recipeList =
        MutableStateFlow<ResultWrapper<RandomRecipeResponse>>(ResultWrapper.GenericError())
    val recipeList = _recipeList.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            _recipeList.value =
                apiService.getRecipeList()

        }
    }
}