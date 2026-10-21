package com.example.segundoparcial.model

data class Catalogo(
    var productos: List<Producto> = mutableListOf()
) {
    fun agregar(producto: Producto) {
        TODO("Not yet implemented")
    }

    fun buscarPorTitulo(texto: String): List<Producto> {
        TODO("Not yet implemented")
    }

    fun filtrarPorCategoria(categoria: Categoria): List<Producto> {
        TODO("Not yet implemented")
    }

    fun filtrarPorPrecio(min: Float, max: Float): List<Producto> {
        TODO("Not yet implemented")
    }

    fun ordenarPorPrecio(ascendente: Boolean): List<Producto> {
        TODO("Not yet implemented")
    }
}
