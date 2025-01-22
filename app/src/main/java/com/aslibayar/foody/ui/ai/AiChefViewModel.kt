package com.aslibayar.foody.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.repository.AIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiChefViewModel(
    private val repository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiChefUiState())
    val uiState = _uiState.asStateFlow()

    fun suggestRecipe(ingredients: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = "") }
            when (val result = repository.suggestRecipe(ingredients)) {
                is BaseUIModel.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipe = result.data
                        )
                    }
                }

                is BaseUIModel.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is BaseUIModel.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}