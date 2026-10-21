package com.example.segundoparcial.model

import java.util.Date

data class TipoCambio(
    var id: Int,
    var fecha: Date,
    var monedaBase: Moneda,
    var monedaDestino: Moneda,
    var tasa: Float
) {
    fun convertir(monto: Float): Float {
        TODO("Not yet implemented")
    }
}
