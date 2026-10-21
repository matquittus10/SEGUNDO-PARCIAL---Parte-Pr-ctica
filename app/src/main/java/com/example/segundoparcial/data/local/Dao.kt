package com.example.segundoparcial.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) de Room para realizar consultas y modificaciones en la base de datos local.
 */
@Dao
interface ElementoDao {

    // Obtener todos los elementos como Flow reactivo (se actualiza automáticamente la UI cuando cambia la BD)
    @Query("SELECT * FROM elementos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<ElementoEntity>>

    // Obtener todos los elementos mediante suspend function
    @Query("SELECT * FROM elementos")
    suspend fun obtenerTodosDirecto(): List<ElementoEntity>

    // Buscar un elemento por ID
    @Query("SELECT * FROM elementos WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): ElementoEntity?

    // Insertar o actualizar un solo elemento
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(elemento: ElementoEntity)

    // Insertar o actualizar una lista de elementos (ideal al descargar de la API)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLista(elementos: List<ElementoEntity>)

    // Actualizar un elemento
    @Update
    suspend fun actualizar(elemento: ElementoEntity)

    // Eliminar un elemento
    @Delete
    suspend fun eliminar(elemento: ElementoEntity)

    // Borrar todos los registros
    @Query("DELETE FROM elementos")
    suspend fun eliminarTodos()
}
