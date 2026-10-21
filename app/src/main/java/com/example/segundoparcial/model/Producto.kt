package com.example.segundoparcial.model

data class Producto(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var precioUSD: Float,
    var imagen: String,
    var categoria: Categoria
) {
    fun precioEn(tipoCambio: TipoCambio): Float {
        TODO("Not yet implemented")
    }

    fun perteneceA(categoria: Categoria): Boolean {
        TODO("Not yet implemented")
    }
}
