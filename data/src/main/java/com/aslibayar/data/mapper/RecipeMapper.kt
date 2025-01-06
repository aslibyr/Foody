package com.aslibayar.data.mapper

import RecipesItem
import com.aslibayar.data.BuildConfig
import com.aslibayar.data.model.AnalyzedInstruction
import com.aslibayar.data.model.Equipment
import com.aslibayar.data.model.Ingredient
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeIngredientsUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.data.model.Step
import com.aslibayar.network.response.ExtendedIngredientsItem
import com.aslibayar.network.response.RecipeDetailResponse
import com.aslibayar.network.response.ResultsItem

fun RecipesItem.toUIModel(): RecipeUIModel {
    return RecipeUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: "",
        vegetarian = this.vegetarian ?: false,
        vegan = this.vegan ?: false,
        glutenFree = this.glutenFree ?: false,
        readyInMinutes = this.readyInMinutes.toString() + " min."
    )
}

fun ResultsItem.toUIModel(): RecipeUIModel {
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

    val instructions = analyzedInstructions?.map { instruction ->
        val steps = instruction.steps?.map { step ->
            Step(
                number = step?.number ?: 0,
                step = step?.step.orEmpty(),
                ingredients = step?.ingredients?.map { ingredient ->
                    Ingredient(
                        id = ingredient?.id ?: 0,
                        image = ingredient?.image.orEmpty(),
                        name = ingredient?.name.orEmpty(),
                        localizedName = ingredient?.localizedName.orEmpty(
                        )
                    )
                } ?: emptyList(),
                equipment = step?.equipment?.map { equipment ->
                    Equipment(
                        id = equipment?.id ?: 0,
                        name = equipment?.name.orEmpty(),
                        localizedName = equipment?.localizedName.orEmpty(),
                        image = equipment?.image.orEmpty()
                    )
                } ?: emptyList()
            )
        } ?: emptyList()

        AnalyzedInstruction(
            name = instruction.name.orEmpty(),
            steps = steps
        )
    } ?: emptyList()

    return RecipeDetailUIModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        image = this.image ?: "",
        summary = this.summary ?: "",
        instructions = this.instructions ?: "",
        extendedIngredients = extendedIngredients,
        sourceUrl = this.sourceUrl ?: "",
        diets = this.diets ?: emptyList(),
        time = (this.readyInMinutes.toString() + " mins."),
        servings = this.servings.toString(),
        analyzedInstructions = instructions
    )
}

fun ExtendedIngredientsItem.toUIModel(): RecipeIngredientsUIModel {
    return RecipeIngredientsUIModel(
        ingredientId = this.id ?: 0,
        image = if (this.image.isNullOrEmpty()) "" else BuildConfig.BASE_IMAGE_URL + this.image,
        name = this.nameClean ?: "",
        original = this.original ?: ""
    )
}

fun RecipeDetailUIModel.toFavoriteRecipeEntity(): com.aslibayar.data.local.entity.FavoriteRecipeEntity {
    return com.aslibayar.data.local.entity.FavoriteRecipeEntity(
        id = this.id,
        title = this.title,
        image = this.image,
    )
}
