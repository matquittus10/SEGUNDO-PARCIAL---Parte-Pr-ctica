package com.example.segundoparcial.data

import com.example.segundoparcial.data.local.ElementoDao
import com.example.segundoparcial.data.local.ElementoEntity
import com.example.segundoparcial.data.repository.ProductoRepository
import com.example.segundoparcial.data.repository.PublicacionRepository
import com.example.segundoparcial.data.repository.TipoCambioRepository
import com.example.segundoparcial.model.Producto
import com.example.segundoparcial.model.Publicacion
import com.example.segundoparcial.model.TipoCambio
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio principal que unifica los 3 repositorios específicos (Productos, Tipos de Cambio, Publicaciones)
 * y la base de datos local (Room).
 */
class Repository(
    private val dao: ElementoDao,
    val productoRepository: ProductoRepository = ProductoRepository(),
    val tipoCambioRepository: TipoCambioRepository = TipoCambioRepository(),
    val publicacionRepository: PublicacionRepository = PublicacionRepository()
) {

    val elementosLocal: Flow<List<ElementoEntity>> = dao.obtenerTodos()

    suspend fun refrescarProductosDesdeApi(): Result<List<Producto>> {
        val resultado = productoRepository.obtenerProductos()
        resultado.onSuccess { productos ->
            val entidades = productos.map {
                ElementoEntity(
                    id = it.id,
                    titulo = it.titulo,
                    descripcion = it.descripcion,
                    precio = it.precioUSD.toDouble()
                )
            }
            dao.insertarLista(entidades)
        }
        return resultado
    }

    suspend fun obtenerTasasDesdeApi(): Result<List<TipoCambio>> {
        return tipoCambioRepository.obtenerTasas()
    }

    suspend fun obtenerPublicacionesDesdeApi(): Result<List<Publicacion>> {
        return publicacionRepository.obtenerPublicaciones()
    }

    suspend fun insertarLocalmente(elemento: ElementoEntity) {
        dao.insertar(elemento)
    }

    suspend fun eliminarLocalmente(elemento: ElementoEntity) {
        dao.eliminar(elemento)
    }
}
