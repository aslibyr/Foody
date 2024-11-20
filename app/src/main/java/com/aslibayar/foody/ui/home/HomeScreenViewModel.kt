package com.aslibayar.foody.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _recipeList =
        MutableStateFlow<BaseUIModel<List<RecipeUIModel?>>>(BaseUIModel.Loading)
    val recipeList = _recipeList.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            repository.getRandomRecipes().collect(
                _recipeList::emit
            )
        }
    }
}