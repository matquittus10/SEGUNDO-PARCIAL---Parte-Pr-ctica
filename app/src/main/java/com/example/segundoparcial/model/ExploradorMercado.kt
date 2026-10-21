package com.example.segundoparcial.model

data class ExploradorMercado(
    var catalogo: Catalogo,
    var mercado: Mercado
) {
    fun listarProductosEn(moneda: Moneda): List<Producto> {
        TODO("Not yet implemented")
    }

    fun compararPrecios(producto: Producto): Map<Moneda, Float> {
        TODO("Not yet implemented")
    }
}
