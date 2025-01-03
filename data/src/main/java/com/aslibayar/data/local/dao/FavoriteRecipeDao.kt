package com.aslibayar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aslibayar.data.local.entity.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(movieEntity: FavoriteRecipeEntity)

    @Query("SELECT * FROM favorite_recipes WHERE id = :id")
    fun getFavoriteRecipe(id: String): FavoriteRecipeEntity?

    @Query("SELECT * FROM favorite_recipes")
    fun getFavoriteRecipes(): Flow<List<FavoriteRecipeEntity>>

    @Query("DELETE FROM favorite_recipes Where id = :id")
    fun removeFavoriteRecipe(id: String)
}