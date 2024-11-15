package com.aslibayar.data.repository

import com.aslibayar.data.mapper.toUIModel
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.network.RecipesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository(private val recipesApiService: RecipesApiService) {

    suspend fun getRandomRecipes(): Flow<BaseUIModel<List<RecipeUIModel?>>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiService.getRecipeList()) {
                is com.aslibayar.network.ResultWrapper.GenericError -> emit(BaseUIModel.Error("Error"))
                is com.aslibayar.network.ResultWrapper.Success -> {
                    val result = response.value.recipes?.map {
                        it?.toUIModel()
                    } ?: emptyList()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }
}