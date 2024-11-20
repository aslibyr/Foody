package com.aslibayar.network

import RandomRecipeResponse
import com.aslibayar.network.response.RecipeDetailResponse
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments

class RecipesApiService(
    private val client: HttpClient
) : RecipesApiServiceImp {

    override suspend fun getRecipeList(): NetworkResult<RandomRecipeResponse> =
        safeApiCall<RandomRecipeResponse>(client) {
                url {
                    appendPathSegments("recipes", "random")
                    parameters.append("number", 10.toString())
                    parameters.append("apiKey", BuildConfig.API_KEY)
                }
            method = HttpMethod.Get
        }

    override suspend fun getRecipeDetail(recipeId: Int): NetworkResult<RecipeDetailResponse> =
        safeApiCall<RecipeDetailResponse>(client) {
            url {
                appendPathSegments("recipes", recipeId.toString(), "information")
                parameters.append("apiKey", BuildConfig.API_KEY)
            }
            method = HttpMethod.Get
        }
}

interface RecipesApiServiceImp {
    suspend fun getRecipeList(): NetworkResult<RandomRecipeResponse>
    suspend fun getRecipeDetail(recipeId: Int): NetworkResult<RecipeDetailResponse>
}