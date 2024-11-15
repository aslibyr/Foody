package com.aslibayar.data.mapper

import RecipesItem
import com.aslibayar.data.model.RecipeUIModel

fun RecipesItem.toUIModel(): RecipeUIModel {
    return RecipeUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
    )
}