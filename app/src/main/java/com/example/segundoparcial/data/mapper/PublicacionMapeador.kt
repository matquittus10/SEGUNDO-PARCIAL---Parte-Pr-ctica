package com.example.segundoparcial.data.mapper

import com.example.segundoparcial.data.remote.dtos.ProductoDto
import com.example.segundoparcial.data.remote.dtos.PublicacionDto
import com.example.segundoparcial.model.Publicacion
import com.example.segundoparcial.model.Usuario

/**
 * Mapeador 2: Conectado a PublicacionRepository y ProductoRepository.
 * Transforma DTOs de publicaciones (JSONPlaceholder) a los modelos Publicacion y Usuario.
 * También permite transformar productos a resúmenes o artículos de publicaciones.
 */
object PublicacionMapeador {

    fun PublicacionDto.toDomain(): Publicacion {
        val uId = this.userId ?: 0
        return Publicacion(
            id = this.id,
            titulo = this.title ?: "Sin título",
            contenido = this.body ?: "",
            autor = Usuario(id = uId, nombre = "Usuario $uId")
        )
    }

    fun List<PublicacionDto>.toDomainPublicaciones(): List<Publicacion> {
        return this.map { it.toDomain() }
    }

    // Método que conecta también con ProductoRepository para convertir productos a publicaciones o noticias
    fun ProductoDto.toPublicacion(): Publicacion {
        val pId = this.id
        return Publicacion(
            id = pId,
            titulo = "Producto: ${this.title ?: "Sin título"}",
            contenido = this.description ?: "",
            autor = Usuario(id = pId, nombre = "Vendedor $pId")
        )
    }
}
