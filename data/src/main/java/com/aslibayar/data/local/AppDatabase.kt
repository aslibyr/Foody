package com.aslibayar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslibayar.data.local.dao.FavoriteRecipeDao
import com.aslibayar.data.local.entity.FavoriteRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRecipes(): FavoriteRecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}