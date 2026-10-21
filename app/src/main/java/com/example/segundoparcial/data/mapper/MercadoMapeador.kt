package com.example.segundoparcial.data.mapper

import com.example.segundoparcial.data.remote.dtos.ProductoDto
import com.example.segundoparcial.data.remote.dtos.TipoCambioRespuestaDto
import com.example.segundoparcial.model.Categoria
import com.example.segundoparcial.model.Moneda
import com.example.segundoparcial.model.Producto
import com.example.segundoparcial.model.TipoCambio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Mapeador 1: Conectado a ProductoRepository y TipoCambioRepository.
 * Mapea las respuestas de DummyJSON (Productos) y Frankfurter (Tipos de Cambio)
 * a los modelos de dominio (Producto, Categoria, TipoCambio, Moneda).
 */
object MercadoMapeador {

    fun ProductoDto.toDomain(): Producto {
        val catNombre = this.category ?: "General"
        return Producto(
            id = this.id,
            titulo = this.title ?: "Sin título",
            descripcion = this.description ?: "",
            precioUSD = this.price ?: 0.0f,
            imagen = this.thumbnail ?: "",
            categoria = Categoria(id = catNombre.hashCode(), nombre = catNombre)
        )
    }

    fun List<ProductoDto>.toDomainProductos(): List<Producto> {
        return this.map { it.toDomain() }
    }

    fun TipoCambioRespuestaDto.toDomainList(): List<TipoCambio> {
        val baseCodigo = this.base ?: "USD"
        val monedaBaseObj = Moneda(codigo = baseCodigo, nombre = "Dólar estadounidense")
        val fechaDate = parseFecha(this.date)
        val lista = mutableListOf<TipoCambio>()

        this.rates?.forEach { (codigoDestino, tasaValor) ->
            val monedaDestinoObj = Moneda(codigo = codigoDestino, nombre = obtenerNombreMoneda(codigoDestino))
            lista.add(
                TipoCambio(
                    id = "$baseCodigo-$codigoDestino".hashCode(),
                    fecha = fechaDate,
                    monedaBase = monedaBaseObj,
                    monedaDestino = monedaDestinoObj,
                    tasa = tasaValor
                )
            )
        }
        return lista
    }

    private fun parseFecha(fechaStr: String?): Date {
        if (fechaStr == null) return Date()
        return try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaStr) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    private fun obtenerNombreMoneda(codigo: String): String {
        return when (codigo.uppercase(Locale.getDefault())) {
            "EUR" -> "Euro"
            "BOB" -> "Boliviano"
            "USD" -> "Dólar estadounidense"
            else -> codigo
        }
    }
}
