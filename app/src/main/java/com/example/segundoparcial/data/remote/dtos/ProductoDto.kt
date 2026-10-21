package com.example.segundoparcial.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class ProductosRespuestaDto(
    @SerializedName("products")
    val products: List<ProductoDto>,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("limit")
    val limit: Int?
)

data class ProductoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("price")
    val price: Float?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("category")
    val category: String?
)
