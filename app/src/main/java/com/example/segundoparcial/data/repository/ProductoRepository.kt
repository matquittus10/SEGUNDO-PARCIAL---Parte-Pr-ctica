package com.example.segundoparcial.data.repository

import com.example.segundoparcial.data.mapper.MercadoMapeador.toDomainProductos
import com.example.segundoparcial.data.mapper.PublicacionMapeador.toPublicacion
import com.example.segundoparcial.data.remote.RetrofitClient
import com.example.segundoparcial.data.remote.api.ProductoApiService
import com.example.segundoparcial.model.Producto
import com.example.segundoparcial.model.Publicacion

/**
 * Repositorio para la API de Productos (https://dummyjson.com/products).
 * Conectado a MercadoMapeador y PublicacionMapeador.
 */
class ProductoRepository(
    private val api: ProductoApiService = RetrofitClient.productoApi
) {
    suspend fun obtenerProductos(): Result<List<Producto>> {
        return runCatching {
            val respuesta = api.obtenerProductos()
            respuesta.products.toDomainProductos()
        }
    }

    suspend fun obtenerProductosComoPublicaciones(): Result<List<Publicacion>> {
        return runCatching {
            val respuesta = api.obtenerProductos()
            respuesta.products.map { it.toPublicacion() }
        }
    }
}
