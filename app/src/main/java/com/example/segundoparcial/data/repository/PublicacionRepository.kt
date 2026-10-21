package com.example.segundoparcial.data.repository

import com.example.segundoparcial.data.mapper.PublicacionMapeador.toDomainPublicaciones
import com.example.segundoparcial.data.remote.RetrofitClient
import com.example.segundoparcial.data.remote.api.PublicacionApiService
import com.example.segundoparcial.model.Publicacion

/**
 * Repositorio para la API de Publicaciones (https://jsonplaceholder.typicode.com/posts).
 * Conectado a PublicacionMapeador.
 */
class PublicacionRepository(
    private val api: PublicacionApiService = RetrofitClient.publicacionApi
) {
    suspend fun obtenerPublicaciones(): Result<List<Publicacion>> {
        return runCatching {
            val publicacionesDto = api.obtenerPublicaciones()
            publicacionesDto.toDomainPublicaciones()
        }
    }
}
