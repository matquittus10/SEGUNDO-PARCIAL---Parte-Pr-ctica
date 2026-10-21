package com.example.segundoparcial.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para deserializar los objetos JSON recibidos desde la API.
 */
data class ElementoDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title") // Ajusta el SerializedName según las llaves exactas de tu JSON
    val titulo: String?,

    @SerializedName("description")
    val descripcion: String?,

    @SerializedName("price")
    val precio: Double?,

    @SerializedName("completed")
    val estaCompletado: Boolean?
)
