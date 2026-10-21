package com.example.segundoparcial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.segundoparcial.data.Repository
import com.example.segundoparcial.data.local.ElementoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface UiState {
    data object Cargando : UiState
    data class Exito(val elementos: List<ElementoEntity>) : UiState
    data class Error(val mensaje: String) : UiState
}

class MainViewModel(private val repository: Repository) : ViewModel() {

    // Flow reactivo directamente conectado a Room
    val elementosState: StateFlow<List<ElementoEntity>> = repository.elementosLocal
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _estadoOperacion = MutableStateFlow<String?>(null)
    val estadoOperacion: StateFlow<String?> = _estadoOperacion.asStateFlow()

    init {
        // Al iniciar el ViewModel se realiza la sincronización con la API
        sincronizarConApi()
    }

    fun sincronizarConApi() {
        viewModelScope.launch {
            _estadoOperacion.value = "Sincronizando con API..."
            repository.refrescarDatosDesdeApi()
                .onSuccess {
                    _estadoOperacion.value = "Sincronización exitosa"
                }
                .onFailure { error ->
                    _estadoOperacion.value = "Error al sincronizar: ${error.localizedMessage}"
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

    // Factory manual para la instanciación del ViewModel con parámetros
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
