package com.aslibayar.foody.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
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

    private val _recipeDetail =
        MutableStateFlow<BaseUIModel<RecipeDetailUIModel>>(BaseUIModel.Loading)
    val recipe = _recipeDetail.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        BaseUIModel.Loading
    )

    init {
        getRecipeDetail(recipeId = recipeId)
    }

    private fun getRecipeDetail(recipeId: Int) {
        viewModelScope.launch {
            repository.getRecipeDetail(recipeId).collect {
                _recipeDetail.emit(it)
            }
        }
    }
}