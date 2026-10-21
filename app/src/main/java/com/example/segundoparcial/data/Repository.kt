package com.example.segundoparcial.data

import com.example.segundoparcial.data.Mapeador.toDto
import com.example.segundoparcial.data.Mapeador.toEntity
import com.example.segundoparcial.data.Mapeador.toEntityList
import com.example.segundoparcial.data.local.ElementoDao
import com.example.segundoparcial.data.local.ElementoEntity
import com.example.segundoparcial.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio unificado para coordinar la base de datos local (Room) y las llamadas a la API (Retrofit).
 */
class Repository(
    private val dao: ElementoDao,
    private val api: ApiService
) {

    // Obtenemos los elementos almacenados localmente de forma reactiva (Flow)
    val elementosLocal: Flow<List<ElementoEntity>> = dao.obtenerTodos()

    // Sincroniza obteniendo los datos de la API e insertándolos en Room
    suspend fun refrescarDatosDesdeApi(): Result<Unit> {
        return runCatching {
            val dtos = api.obtenerElementos()
            val entidades = dtos.toEntityList()
            dao.insertarLista(entidades)
        }
    }

    // Insertar un solo elemento localmente
    suspend fun insertarLocalmente(elemento: ElementoEntity) {
        dao.insertar(elemento)
    }

    // Actualizar un elemento localmente
    suspend fun actualizarLocalmente(elemento: ElementoEntity) {
        dao.actualizar(elemento)
    }

    // Eliminar un elemento localmente
    suspend fun eliminarLocalmente(elemento: ElementoEntity) {
        dao.eliminar(elemento)
    }

    // Obtener un elemento por su ID desde Room
    suspend fun obtenerPorId(id: Int): ElementoEntity? {
        return dao.obtenerPorId(id)
    }

    // Guardar en API y actualizar Room con la respuesta
    suspend fun crearEnApiYLocal(elemento: ElementoEntity): Result<Unit> {
        return runCatching {
            val dto = elemento.toDto()
            val respuestaDto = api.crearElemento(dto)
            dao.insertar(respuestaDto.toEntity())
        }
    }
}
