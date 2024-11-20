package com.aslibayar.data.mapper

import RecipesItem
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.network.response.RecipeDetailResponse

fun RecipesItem.toUIModel(): RecipeUIModel {
    return RecipeUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: ""
    )
}

fun RecipeDetailResponse.toUIModel(): RecipeDetailUIModel {
    return RecipeDetailUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: "",
        summary = this.summary ?: "",
        instructions = this.instructions ?: ""
    )
}