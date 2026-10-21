package com.example.segundoparcial.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Definición de endpoints de Retrofit.
 */
interface ApiService {

    // Obtener todos los elementos desde la API
    @GET("elementos") // Cambia la ruta por la correspondiente a tu examen (ej: "todos", "posts", "products")
    suspend fun obtenerElementos(): List<ElementoDto>

    // Obtener un elemento por su ID
    @GET("elementos/{id}")
    suspend fun obtenerElementoPorId(@Path("id") id: Int): ElementoDto

    // Crear un elemento
    @POST("elementos")
    suspend fun crearElemento(@Body elemento: ElementoDto): ElementoDto

    // Actualizar un elemento
    @PUT("elementos/{id}")
    suspend fun actualizarElemento(@Path("id") id: Int, @Body elemento: ElementoDto): ElementoDto

    // Eliminar un elemento
    @DELETE("elementos/{id}")
    suspend fun eliminarElemento(@Path("id") id: Int)
}
