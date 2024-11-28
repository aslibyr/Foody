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

    private val _uiState =
        MutableStateFlow(SearchScreenUIStateModel())
    val uiState = _uiState.asStateFlow()

    fun searchRecipe(query: String? = null) {
        val searchQuery = query ?: uiState.value.searchQuery
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchRecipe(searchQuery).collect { result ->
                when (result) {
                    is BaseUIModel.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is BaseUIModel.Success -> {
                        _uiState.update {
                            it.copy(
                                recipes = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is BaseUIModel.Error -> {
                        _uiState.update {
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

    private fun autoComplete(query: String) {
        viewModelScope.launch {
            repository.autoComplete(query).collect { result ->
                if (result is BaseUIModel.Success) {
                    _uiState.update { it.copy(autoCompleteList = result.data) }
                }
                _uiState.update { it.copy(isLoading = result is BaseUIModel.Loading) }
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.length > 2 && _uiState.value.isLoading.not()) {
            autoComplete(query)
        }
        if (query.isEmpty()) {
            _uiState.update { it.copy(autoCompleteList = emptyList(), recipes = emptyList()) }
        }
    }
}


