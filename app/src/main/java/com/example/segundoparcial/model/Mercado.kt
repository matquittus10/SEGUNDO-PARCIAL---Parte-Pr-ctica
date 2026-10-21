package com.example.segundoparcial.model

data class Mercado(
    var tasas: List<TipoCambio> = mutableListOf()
) {
    fun agregarTasa(tipoCambio: TipoCambio) {
        TODO("Not yet implemented")
    }

    fun obtenerTasa(destino: Moneda): TipoCambio? {
        TODO("Not yet implemented")
    }

    fun tasaMasReciente(destino: Moneda): TipoCambio? {
        TODO("Not yet implemented")
    }
}
