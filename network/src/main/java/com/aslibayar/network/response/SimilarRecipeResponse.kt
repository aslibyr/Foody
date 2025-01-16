package com.aslibayar.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarRecipeResponseItem(

    @SerialName("readyInMinutes")
    val readyInMinutes: Int? = null,

    @SerialName("sourceUrl")
    val sourceUrl: String? = null,

    @SerialName("servings")
    val servings: Int? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("imageType")
    val imageType: String? = null
)
