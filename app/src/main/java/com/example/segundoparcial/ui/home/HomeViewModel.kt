package com.example.segundoparcial.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.segundoparcial.data.repository.ProductoRepository
import com.example.segundoparcial.data.repository.PublicacionRepository
import com.example.segundoparcial.data.repository.TipoCambioRepository
import com.example.segundoparcial.model.Producto
import com.example.segundoparcial.model.Publicacion
import com.example.segundoparcial.model.TipoCambio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productoRepository: ProductoRepository = ProductoRepository(),
    private val tipoCambioRepository: TipoCambioRepository = TipoCambioRepository(),
    private val publicacionRepository: PublicacionRepository = PublicacionRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _tasas = MutableStateFlow<List<TipoCambio>>(emptyList())
    val tasas: StateFlow<List<TipoCambio>> = _tasas.asStateFlow()

    private val _publicaciones = MutableStateFlow<List<Publicacion>>(emptyList())
    val publicaciones: StateFlow<List<Publicacion>> = _publicaciones.asStateFlow()

    private val _cargandoProductos = MutableStateFlow(false)
    val cargandoProductos: StateFlow<Boolean> = _cargandoProductos.asStateFlow()

    private val _cargandoTasas = MutableStateFlow(false)
    val cargandoTasas: StateFlow<Boolean> = _cargandoTasas.asStateFlow()

    private val _cargandoPublicaciones = MutableStateFlow(false)
    val cargandoPublicaciones: StateFlow<Boolean> = _cargandoPublicaciones.asStateFlow()

    init {
        cargarDatosDeApis()
    }

    fun cargarDatosDeApis() {
        cargarProductos()
        cargarTasas()
        cargarPublicaciones()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _cargandoProductos.value = true
            productoRepository.obtenerProductos()
                .onSuccess { _productos.value = it }
            _cargandoProductos.value = false
        }
    }

    fun cargarTasas() {
        viewModelScope.launch {
            _cargandoTasas.value = true
            tipoCambioRepository.obtenerTasas()
                .onSuccess { _tasas.value = it }
            _cargandoTasas.value = false
        }
    }

    fun cargarPublicaciones() {
        viewModelScope.launch {
            _cargandoPublicaciones.value = true
            publicacionRepository.obtenerPublicaciones()
                .onSuccess { _publicaciones.value = it }
            _cargandoPublicaciones.value = false
        }
    }

    class Factory(
        private val productoRepository: ProductoRepository = ProductoRepository(),
        private val tipoCambioRepository: TipoCambioRepository = TipoCambioRepository(),
        private val publicacionRepository: PublicacionRepository = PublicacionRepository()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
                    productoRepository,
                    tipoCambioRepository,
                    publicacionRepository
                ) as T
            }
            throw IllegalArgumentException("ViewModel no reconocido")
        }
    }
}
