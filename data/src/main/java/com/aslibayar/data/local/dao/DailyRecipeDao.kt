package com.aslibayar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aslibayar.data.local.entity.DailyRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyRecipes(recipes: List<DailyRecipeEntity>)

    @Query("SELECT * FROM daily_recipes")
    fun getDailyRecipes(): Flow<List<DailyRecipeEntity>>

    @Query("DELETE FROM daily_recipes")
    suspend fun clearDailyRecipes()

    @Query("SELECT timestamp FROM daily_recipes LIMIT 1")
    suspend fun getLastUpdateTime(): Long?
} 