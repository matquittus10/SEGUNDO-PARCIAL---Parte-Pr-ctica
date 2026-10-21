package com.example.segundoparcial.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class TipoCambioRespuestaDto(
    @SerializedName("amount")
    val amount: Float?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val rates: Map<String, Float>?
)
