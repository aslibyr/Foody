package com.aslibayar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslibayar.data.local.dao.FavoriteRecipeDao
import com.aslibayar.data.local.entity.FavoriteRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipes(): FavoriteRecipeDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "recipe_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}