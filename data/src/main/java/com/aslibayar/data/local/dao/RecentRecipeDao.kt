package com.aslibayar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aslibayar.data.local.entity.RecentRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentRecipeDao {
    @Query("SELECT * FROM recent_recipes ORDER BY timestamp DESC LIMIT 10")
    fun getRecentRecipes(): Flow<List<RecentRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentRecipe(recipe: RecentRecipeEntity)

    @Query("DELETE FROM recent_recipes WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

    @Query(
        """
        DELETE FROM recent_recipes 
        WHERE id IN (
            SELECT id FROM recent_recipes 
            ORDER BY timestamp ASC 
            LIMIT max(0, (SELECT COUNT(*) FROM recent_recipes) - 10)
        )
    """
    )
    suspend fun deleteOldRecipes()
} 