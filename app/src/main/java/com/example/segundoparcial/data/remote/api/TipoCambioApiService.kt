package com.example.segundoparcial.data.remote.api

import com.example.segundoparcial.data.remote.dtos.TipoCambioRespuestaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TipoCambioApiService {
    @GET("v1/latest")
    suspend fun obtenerTasas(
        @Query("base") base: String = "USD"
    ): TipoCambioRespuestaDto
}
