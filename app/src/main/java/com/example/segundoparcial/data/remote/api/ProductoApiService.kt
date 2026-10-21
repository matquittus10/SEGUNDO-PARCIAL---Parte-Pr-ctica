package com.example.segundoparcial.data.remote.api

import com.example.segundoparcial.data.remote.dtos.ProductosRespuestaDto
import retrofit2.http.GET

interface ProductoApiService {
    @GET("products")
    suspend fun obtenerProductos(): ProductosRespuestaDto
}
