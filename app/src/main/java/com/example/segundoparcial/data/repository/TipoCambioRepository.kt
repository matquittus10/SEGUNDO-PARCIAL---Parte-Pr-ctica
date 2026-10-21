package com.example.segundoparcial.data.repository

import com.example.segundoparcial.data.mapper.MercadoMapeador.toDomainList
import com.example.segundoparcial.data.remote.RetrofitClient
import com.example.segundoparcial.data.remote.api.TipoCambioApiService
import com.example.segundoparcial.model.Moneda
import com.example.segundoparcial.model.TipoCambio
import java.util.Date

/**
 * Repositorio para la API de Tipos de Cambio (https://api.frankfurter.dev/v1/latest).
 * Conectado a MercadoMapeador.
 */
class TipoCambioRepository(
    private val api: TipoCambioApiService = RetrofitClient.tipoCambioApi
) {
    suspend fun obtenerTasas(base: String = "USD"): Result<List<TipoCambio>> {
        return runCatching {
            val respuesta = api.obtenerTasas(base)
            val lista = respuesta.toDomainList()
            if (lista.isNotEmpty()) {
                lista
            } else {
                obtenerTasasFallback()
            }
        }.recover {
            obtenerTasasFallback()
        }
    }

    private fun obtenerTasasFallback(): List<TipoCambio> {
        val usd = Moneda("USD", "Dólar estadounidense")
        val hoy = Date()
        return listOf(
            TipoCambio(1, hoy, usd, Moneda("EUR", "Euro"), 0.95f),
            TipoCambio(2, hoy, usd, Moneda("BOB", "Boliviano"), 6.96f),
            TipoCambio(3, hoy, usd, Moneda("GBP", "Libra esterlina"), 0.79f),
            TipoCambio(4, hoy, usd, Moneda("BRL", "Real brasileño"), 5.72f)
        )
    }
}
