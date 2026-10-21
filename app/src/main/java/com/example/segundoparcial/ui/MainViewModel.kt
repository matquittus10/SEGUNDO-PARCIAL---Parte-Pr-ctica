package com.example.segundoparcial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.segundoparcial.data.Repository
import com.example.segundoparcial.data.local.ElementoEntity
import com.example.segundoparcial.model.Publicacion
import com.example.segundoparcial.model.TipoCambio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // Lista en vivo proveniente de Room
    val elementosState: StateFlow<List<ElementoEntity>> = repository.elementosLocal
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _estadoOperacion = MutableStateFlow<String?>(null)
    val estadoOperacion: StateFlow<String?> = _estadoOperacion.asStateFlow()

    private val _tasasState = MutableStateFlow<List<TipoCambio>>(emptyList())
    val tasasState: StateFlow<List<TipoCambio>> = _tasasState.asStateFlow()

    private val _publicacionesState = MutableStateFlow<List<Publicacion>>(emptyList())
    val publicacionesState: StateFlow<List<Publicacion>> = _publicacionesState.asStateFlow()

    init {
        sincronizarTodo()
    }

    fun sincronizarTodo() {
        sincronizarProductos()
        cargarTasas()
        cargarPublicaciones()
    }

    fun sincronizarProductos() {
        viewModelScope.launch {
            _estadoOperacion.value = "Cargando productos..."
            repository.refrescarProductosDesdeApi()
                .onSuccess {
                    _estadoOperacion.value = "Productos cargados exitosamente"
                }
                .onFailure { error ->
                    _estadoOperacion.value = "Error en productos: ${error.localizedMessage}"
                }
        }
    }

    fun cargarTasas() {
        viewModelScope.launch {
            repository.obtenerTasasDesdeApi()
                .onSuccess { tasas ->
                    _tasasState.value = tasas
                }
        }
    }

    fun cargarPublicaciones() {
        viewModelScope.launch {
            repository.obtenerPublicacionesDesdeApi()
                .onSuccess { publicaciones ->
                    _publicacionesState.value = publicaciones
                }
        }
    }

    fun agregarElementoLocal(elemento: ElementoEntity) {
        viewModelScope.launch {
            repository.insertarLocalmente(elemento)
        }
    }

    fun eliminarElementoLocal(elemento: ElementoEntity) {
        viewModelScope.launch {
            repository.eliminarLocalmente(elemento)
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel class no encontrada")
        }
    }
}
