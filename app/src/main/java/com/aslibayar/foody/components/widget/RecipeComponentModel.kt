package com.aslibayar.foody.components.widget

import com.aslibayar.data.model.RecipeUIModel

data class RecipeWidgetComponentModel(
//    val widgetCategory: String = "",
    val recipes: List<RecipeUIModel?> = emptyList(),
    val isLoading: Boolean = false,
)

