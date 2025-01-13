package com.aslibayar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslibayar.data.local.dao.DailyRecipeDao
import com.aslibayar.data.local.dao.FavoriteRecipeDao
import com.aslibayar.data.local.dao.RecentRecipeDao
import com.aslibayar.data.local.entity.DailyRecipeEntity
import com.aslibayar.data.local.entity.FavoriteRecipeEntity
import com.aslibayar.data.local.entity.RecentRecipeEntity

@Database(
    entities = [
        FavoriteRecipeEntity::class,
        DailyRecipeEntity::class,
        RecentRecipeEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipes(): FavoriteRecipeDao
    abstract fun dailyRecipes(): DailyRecipeDao
    abstract fun recentRecipes(): RecentRecipeDao

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