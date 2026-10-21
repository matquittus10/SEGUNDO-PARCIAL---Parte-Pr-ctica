package com.example.segundoparcial.data.remote.api

import com.example.segundoparcial.data.remote.dtos.PublicacionDto
import retrofit2.http.GET

interface PublicacionApiService {
    @GET("posts")
    suspend fun obtenerPublicaciones(): List<PublicacionDto>
}
