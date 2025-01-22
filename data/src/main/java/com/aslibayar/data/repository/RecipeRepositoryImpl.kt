package com.aslibayar.data.repository

import com.aslibayar.data.local.AppDatabase
import com.aslibayar.data.local.entity.DailyRecipeEntity
import com.aslibayar.data.local.entity.RecentRecipeEntity
import com.aslibayar.data.mapper.toFavoriteRecipeEntity
import com.aslibayar.data.mapper.toRecipeUIModel
import com.aslibayar.data.mapper.toUIModel
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.network.NetworkResult
import com.aslibayar.network.RecipesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val recipesApiServiceImp: RecipesApiService,
    private val appDatabase: AppDatabase
) : RecipeRepository {
    private val TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000

    override fun getRandomRecipes(): Flow<BaseUIModel<List<RecipeUIModel?>>> = flow {
        emit(BaseUIModel.Loading)

        try {
            // API'den tüm tarifleri al
            when (val response = recipesApiServiceImp.getRecipeList()) {
                is NetworkResult.Success -> {
                    val allRecipes = response.data.recipes?.map { it?.toUIModel() } ?: emptyList()

                    // Today's Special için ilk 5 tarifi kaydet
                    val lastUpdateTime = appDatabase.dailyRecipes().getLastUpdateTime()
                    val shouldUpdateTodaysSpecial = shouldFetchNewRecipes(lastUpdateTime)

                    if (shouldUpdateTodaysSpecial) {
                        val todaysSpecial = allRecipes.take(5)
                        saveDailyRecipes(todaysSpecial.filterNotNull())
                    }

                    // Tüm tarifleri emit et
                    emit(BaseUIModel.Success(allRecipes))
                }

                is NetworkResult.Error -> {
                    emit(BaseUIModel.Error("Network error"))
                }
            }
        } catch (e: Exception) {
            emit(BaseUIModel.Error(e.message ?: "Unknown error"))
        }
    }

    // Today's Special tarifleri için ayrı flow
    override fun getTodaysSpecialRecipes(): Flow<List<RecipeUIModel>> {
        return appDatabase.dailyRecipes().getDailyRecipes()
            .map { entities -> entities.map { it.toUIModel() } }
    }

    // Recent Recipes için metodlar
    override suspend fun addToRecentRecipes(recipe: RecipeDetailUIModel) {
        withContext(Dispatchers.IO) {
            val recentRecipe = RecentRecipeEntity(
                id = recipe.id,
                title = recipe.title,
                image = recipe.image,
                time = recipe.time
            )
            // Önce eski kaydı sil (varsa)
            appDatabase.recentRecipes().deleteRecipeById(recipe.id)
            // Yeni kaydı ekle
            appDatabase.recentRecipes().insertRecentRecipe(recentRecipe)
            // 10'dan fazla kayıt varsa en eski kaydı sil
            appDatabase.recentRecipes().deleteOldRecipes()
        }
    }

    override fun getRecentRecipes(): Flow<List<RecipeUIModel>> {
        return appDatabase.recentRecipes()
            .getRecentRecipes()
            .map { entities -> entities.map { it.toRecipeUIModel() } }
    }

    override fun shouldFetchNewRecipes(lastUpdateTime: Long?): Boolean {
        if (lastUpdateTime == null) return true
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastUpdateTime) >= TWENTY_FOUR_HOURS
    }

    override suspend fun saveDailyRecipes(recipes: List<RecipeUIModel>) =
        withContext(Dispatchers.IO) {
            val entities = recipes.map { recipe ->
                DailyRecipeEntity(
                    id = recipe.id,
                    title = recipe.title,
                    image = recipe.image,
                    time = recipe.readyInMinutes,
                )
            }
            appDatabase.dailyRecipes().clearDailyRecipes()
            appDatabase.dailyRecipes().insertDailyRecipes(entities)
        }

    override fun getRecipeDetail(recipeId: Int): Flow<BaseUIModel<RecipeDetailUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiServiceImp.getRecipeDetail(recipeId)) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.toUIModel()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }

    override fun searchRecipe(query: String): Flow<BaseUIModel<List<RecipeUIModel?>>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiServiceImp.searchRecipe(query)) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.results?.map {
                        it?.toUIModel()
                    } ?: emptyList()
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }

    override suspend fun addRecipeToFavorite(recipe: RecipeDetailUIModel) = withContext(Dispatchers.IO) {
        appDatabase.favoriteRecipes().insertFavoriteRecipe(recipe.toFavoriteRecipeEntity())
    }

    override suspend fun removeRecipeFromFavorite(recipe: RecipeDetailUIModel) =
        withContext(Dispatchers.IO) {
            appDatabase.favoriteRecipes().removeFavoriteRecipe(recipe.id.toString())
        }

    override suspend fun isFavorite(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        appDatabase.favoriteRecipes().getFavoriteRecipe(recipeId.toString()) != null
    }

    override fun getFavoriteRecipes(): Flow<List<RecipeUIModel>> {
        return appDatabase.favoriteRecipes()
            .getFavoriteRecipes()
            .map { entities -> entities.map { it.toRecipeUIModel() } }
    }

    override fun autoComplete(query: String): Flow<BaseUIModel<List<String>>> {
        return flow {
            emit(BaseUIModel.Loading)
            when (val response = recipesApiServiceImp.autoComplete(query)) {
                is NetworkResult.Error -> emit(BaseUIModel.Error("Error"))
                is NetworkResult.Success -> {
                    val result = response.data.map { it.title ?: "" }
                    emit(BaseUIModel.Success(result))
                }
            }
        }
    }

    override fun getSimilarRecipesWithDetails(recipeId: Int): Flow<BaseUIModel<List<RecipeUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            try {
                when (val response = recipesApiServiceImp.getSimilarRecipes(recipeId)) {
                    is NetworkResult.Success -> {
                        val detailedRecipes = mutableListOf<RecipeUIModel>()

                        response.data.forEach { similarRecipe ->
                            similarRecipe.id?.let { id ->
                                when (val detailResponse =
                                    recipesApiServiceImp.getRecipeDetail(id)) {
                                    is NetworkResult.Success -> {
                                        val recipeWithImage =
                                            similarRecipe.toRecipeUIModel(detailResponse.data.image)
                                        detailedRecipes.add(recipeWithImage)
                                    }

                                    is NetworkResult.Error -> {
                                        emit(BaseUIModel.Error("Error fetching similar recipes"))
                                    }
                                }
                            }
                        }

                        emit(BaseUIModel.Success(detailedRecipes))
                    }

                    is NetworkResult.Error -> {
                        emit(BaseUIModel.Error("Error fetching similar recipes"))
                    }
                }
            } catch (e: Exception) {
                emit(BaseUIModel.Error(e.message ?: "Unknown error"))
            }
        }
    }
}