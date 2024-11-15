package com.aslibayar.network

import RandomRecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers

class RecipesApiService(
    private val client: HttpClient
) {

    suspend fun getRecipeList(): ResultWrapper<RandomRecipeResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get {
                url {
                    appendPathSegments("recipes", "random")
                    parameters.append("number", 50.toString())
                }
            }.body()
        }
}