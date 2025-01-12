package com.aslibayar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_recipes")
data class DailyRecipeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val time: String,
    val timestamp: Long = System.currentTimeMillis()
)