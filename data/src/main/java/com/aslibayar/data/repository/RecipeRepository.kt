package com.aslibayar.data.repository

import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRandomRecipes(): Flow<BaseUIModel<List<RecipeUIModel?>>>
    fun getTodaysSpecialRecipes(): Flow<List<RecipeUIModel>>
    suspend fun addToRecentRecipes(recipe: RecipeDetailUIModel)
    fun getRecentRecipes(): Flow<List<RecipeUIModel>>
    fun shouldFetchNewRecipes(lastUpdateTime: Long?): Boolean
    suspend fun saveDailyRecipes(recipes: List<RecipeUIModel>)
    fun getRecipeDetail(recipeId: Int): Flow<BaseUIModel<RecipeDetailUIModel>>
    fun searchRecipe(query: String): Flow<BaseUIModel<List<RecipeUIModel?>>>
    suspend fun addRecipeToFavorite(recipe: RecipeDetailUIModel)
    suspend fun removeRecipeFromFavorite(recipe: RecipeDetailUIModel)
    suspend fun isFavorite(recipeId: Int): Boolean
    fun getFavoriteRecipes(): Flow<List<RecipeUIModel>>
    fun autoComplete(query: String): Flow<BaseUIModel<List<String>>>
    fun getSimilarRecipesWithDetails(recipeId: Int): Flow<BaseUIModel<List<RecipeUIModel>>>
}