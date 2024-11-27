package com.aslibayar.data.repository

import com.aslibayar.data.mapper.toUIModel
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.network.NetworkResult
import com.aslibayar.network.RecipesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository(private val recipesApiService: RecipesApiService) {

    fun getRandomRecipes(): Flow<BaseUIModel<List<RecipeUIModel?>>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiService.getRecipeList()) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.recipes?.map {
                        it?.toUIModel()
                    } ?: emptyList()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }

    fun getRecipeDetail(recipeId: Int): Flow<BaseUIModel<RecipeDetailUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiService.getRecipeDetail(recipeId)) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.toUIModel()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }

    fun searchRecipe(query: String): Flow<BaseUIModel<List<RecipeUIModel?>>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiService.searchRecipe(query)) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.recipes?.map {
                        it?.toUIModel()
                    } ?: emptyList()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }
}