package com.example.segundoparcial.data.remote.api

import com.example.segundoparcial.data.remote.dtos.TipoCambioRespuestaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TipoCambioApiService {
    @GET("v2/rates")
    suspend fun obtenerTasas(
        @Query("base") base: String = "USD",
        @Query("quotes") quotes: String = "EUR,BOB"
    ): TipoCambioRespuestaDto
}
