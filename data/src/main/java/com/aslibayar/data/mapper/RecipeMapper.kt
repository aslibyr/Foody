package com.aslibayar.data.mapper

import RecipesItem
import com.aslibayar.data.BuildConfig
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeIngredientsUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.network.response.ExtendedIngredientsItem
import com.aslibayar.network.response.RecipeDetailResponse

fun RecipesItem.toUIModel(): RecipeUIModel {
    return RecipeUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: ""
    )
}

fun RecipeDetailResponse.toUIModel(): RecipeDetailUIModel {
    val extendedIngredients = this.extendedIngredients?.map {
        it.toUIModel()
    } ?: emptyList()

    return RecipeDetailUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: "",
        summary = this.summary ?: "",
        instructions = this.instructions ?: "",
        extendedIngredients = extendedIngredients
    )
}

fun ExtendedIngredientsItem.toUIModel(): RecipeIngredientsUIModel {
    return RecipeIngredientsUIModel(
        ingredientId = this.id ?: 0,
        image = BuildConfig.BASE_IMAGE_URL + this.image,
        name = this.nameClean ?: ""
    )
}