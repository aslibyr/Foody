package com.aslibayar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslibayar.data.local.dao.DailyRecipeDao
import com.aslibayar.data.local.dao.FavoriteRecipeDao
import com.aslibayar.data.local.entity.DailyRecipeEntity
import com.aslibayar.data.local.entity.FavoriteRecipeEntity

@Database(
    entities = [
        FavoriteRecipeEntity::class,
        DailyRecipeEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipes(): FavoriteRecipeDao
    abstract fun dailyRecipes(): DailyRecipeDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}