package com.example.segundoparcial.model

data class Publicacion(
    var id: Int,
    var titulo: String,
    var contenido: String,
    var autor: Usuario
) {
    fun resumen(maxCaracteres: Int): String {
        TODO("Not yet implemented")
    }
}
