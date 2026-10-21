package com.example.segundoparcial.data

import com.example.segundoparcial.data.local.ElementoEntity
import com.example.segundoparcial.data.remote.ElementoDto

/**
 * Mapeador de conversión entre objetos DTO (red) y Entity (base de datos Room).
 */
object Mapeador {

    fun ElementoDto.toEntity(): ElementoEntity {
        return ElementoEntity(
            id = this.id,
            titulo = this.titulo ?: "Sin título",
            descripcion = this.descripcion ?: "",
            precio = this.precio ?: 0.0,
            estaCompletado = this.estaCompletado ?: false
        )
    }

    fun ElementoEntity.toDto(): ElementoDto {
        return ElementoDto(
            id = this.id,
            titulo = this.titulo,
            descripcion = this.descripcion,
            precio = this.precio,
            estaCompletado = this.estaCompletado
        )
    }

    fun List<ElementoDto>.toEntityList(): List<ElementoEntity> {
        return this.map { it.toEntity() }
    }

    fun List<ElementoEntity>.toDtoList(): List<ElementoDto> {
        return this.map { it.toDto() }
    }
}
