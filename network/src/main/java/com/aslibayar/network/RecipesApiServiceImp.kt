package com.aslibayar.network

import RandomRecipeResponse
import com.aslibayar.network.response.AutoCompleteResponseItem
import com.aslibayar.network.response.RecipeDetailResponse
import com.aslibayar.network.response.SearchResponse
import com.aslibayar.network.response.SimilarRecipeResponseItem
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments

class RecipesApiServiceImp(
    private val client: HttpClient
) : RecipesApiService {

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

    override suspend fun getSimilarRecipes(recipeId: Int): NetworkResult<List<SimilarRecipeResponseItem>> =
        safeApiCall<List<SimilarRecipeResponseItem>>(client) {
            url {
                appendPathSegments("recipes", recipeId.toString(), "similar")
                parameters.append("number", 3.toString())
                parameters.append("apiKey", BuildConfig.API_KEY)
            }
            method = HttpMethod.Get
        }

    override suspend fun searchRecipe(query: String): NetworkResult<SearchResponse> =
        safeApiCall<SearchResponse>(client) {
            url {
                appendPathSegments("recipes", "complexSearch")
                parameters.append("query", query)
                parameters.append("apiKey", BuildConfig.API_KEY)
            }
            method = HttpMethod.Get
        }

    override suspend fun autoComplete(query: String): NetworkResult<List<AutoCompleteResponseItem>> =
        safeApiCall<List<AutoCompleteResponseItem>>(client) {
            url {
                appendPathSegments("recipes", "autocomplete")
                parameters.append("query", query)
                parameters.append("number", 3.toString())
                parameters.append("apiKey", BuildConfig.API_KEY)
            }
            method = HttpMethod.Get
        }
}

interface RecipesApiService {
    suspend fun getRecipeList(): NetworkResult<RandomRecipeResponse>
    suspend fun getRecipeDetail(recipeId: Int): NetworkResult<RecipeDetailResponse>
    suspend fun getSimilarRecipes(recipeId: Int): NetworkResult<List<SimilarRecipeResponseItem>>
    suspend fun searchRecipe(query: String): NetworkResult<SearchResponse>
    suspend fun autoComplete(query: String): NetworkResult<List<AutoCompleteResponseItem>>
}