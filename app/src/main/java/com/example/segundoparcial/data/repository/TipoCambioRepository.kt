package com.example.segundoparcial.data.repository

import com.example.segundoparcial.data.mapper.MercadoMapeador.toDomainList
import com.example.segundoparcial.data.remote.RetrofitClient
import com.example.segundoparcial.data.remote.api.TipoCambioApiService
import com.example.segundoparcial.model.TipoCambio

/**
 * Repositorio para la API de Tipos de Cambio (https://api.frankfurter.dev/v2/rates).
 * Conectado a MercadoMapeador.
 */
class TipoCambioRepository(
    private val api: TipoCambioApiService = RetrofitClient.tipoCambioApi
) {
    suspend fun obtenerTasas(base: String = "USD", quotes: String = "EUR,BOB"): Result<List<TipoCambio>> {
        return runCatching {
            val respuesta = api.obtenerTasas(base, quotes)
            respuesta.toDomainList()
        }
    }
}
