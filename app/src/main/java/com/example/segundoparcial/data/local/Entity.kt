package com.example.segundoparcial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room para la base de datos local.
 * NOTA DE EXAMEN: Modifica los campos y el nombre de la tabla según lo que pida tu examen.
 */
@Entity(tableName = "elementos")
data class ElementoEntity(
    @PrimaryKey(autoGenerate = false) // Si la API provee ID único usa false. Si insertas localmente sin ID de API, pon true.
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val precio: Double = 0.0,
    val estaCompletado: Boolean = false
)
