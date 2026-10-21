package com.example.segundoparcial.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class PublicacionDto(
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("body")
    val body: String?
)
