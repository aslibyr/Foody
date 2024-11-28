package com.aslibayar.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(

    @SerialName("number")
    val number: Int? = null,

    @SerialName("totalResults")
    val totalResults: Int? = null,

    @SerialName("offset")
    val offset: Int? = null,

    @SerialName("results")
    val results: List<ResultsItem?>? = null
)

@Serializable
data class ResultsItem(

    @SerialName("image")
    val image: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("imageType")
    val imageType: String? = null
)
