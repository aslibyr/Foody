package com.aslibayar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_recipes")
data class FavoriteRecipeEntity(
    @PrimaryKey
    val id: Int = 0,
    val title: String,
    val image: String,
    val time: String
)
