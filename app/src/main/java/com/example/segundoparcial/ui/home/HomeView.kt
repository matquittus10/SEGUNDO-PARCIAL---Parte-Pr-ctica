package com.example.segundoparcial.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segundoparcial.model.Categoria
import com.example.segundoparcial.model.Moneda
import com.example.segundoparcial.model.Producto
import com.example.segundoparcial.model.Publicacion
import com.example.segundoparcial.model.TipoCambio
import com.example.segundoparcial.model.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeView(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory())
) {
    val productos by viewModel.productos.collectAsState()
    val tasas by viewModel.tasas.collectAsState()
    val publicaciones by viewModel.publicaciones.collectAsState()

    val cargandoProductos by viewModel.cargandoProductos.collectAsState()
    val cargandoTasas by viewModel.cargandoTasas.collectAsState()
    val cargandoPublicaciones by viewModel.cargandoPublicaciones.collectAsState()

    var pestanaSeleccionada by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            HomeTopBar(pestanaSeleccionada = pestanaSeleccionada)
        },
        bottomBar = {
            HomeBottomNavigation(
                pestanaSeleccionada = pestanaSeleccionada,
                onPestanaSeleccionada = { pestanaSeleccionada = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (pestanaSeleccionada) {
                0 -> PantallaProductos(
                    productos = productos,
                    cargando = cargandoProductos
                )
                1 -> PantallaTasas(
                    tasas = tasas,
                    cargando = cargandoTasas
                )
                2 -> PantallaPublicaciones(
                    publicaciones = publicaciones,
                    cargando = cargandoPublicaciones
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(pestanaSeleccionada: Int) {
    val titulo = when (pestanaSeleccionada) {
        0 -> "Catálogo de Productos"
        1 -> "Tipos de Cambio"
        else -> "Publicaciones de Usuarios"
    }

    TopAppBar(
        title = { Text(titulo, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun HomeBottomNavigation(
    pestanaSeleccionada: Int,
    onPestanaSeleccionada: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = pestanaSeleccionada == 0,
            onClick = { onPestanaSeleccionada(0) },
            icon = { Icon(Icons.Default.ShoppingBag, contentDescription = "Productos") },
            label = { Text("Productos") }
        )
        NavigationBarItem(
            selected = pestanaSeleccionada == 1,
            onClick = { onPestanaSeleccionada(1) },
            icon = { Icon(Icons.Default.CurrencyExchange, contentDescription = "Tasas") },
            label = { Text("Tasas") }
        )
        NavigationBarItem(
            selected = pestanaSeleccionada == 2,
            onClick = { onPestanaSeleccionada(2) },
            icon = { Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Publicaciones") },
            label = { Text("Publicaciones") }
        )
    }
}

// ---------------------------------------------------------------------------
// PANTALLA 1: PRODUCTOS
// ---------------------------------------------------------------------------
@Composable
fun PantallaProductos(productos: List<Producto>, cargando: Boolean) {
    if (cargando && productos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (productos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontraron productos.")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos) { producto ->
                ProductoCard(producto = producto)
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = producto.categoria.nombre.take(3).uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$${producto.precioUSD} USD",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// PANTALLA 2: TIPOS DE CAMBIO
// ---------------------------------------------------------------------------
@Composable
fun PantallaTasas(tasas: List<TipoCambio>, cargando: Boolean) {
    if (cargando && tasas.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (tasas.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay tipos de cambio disponibles.")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasas) { tasa ->
                TasaDetalleCard(tasa = tasa)
            }
        }
    }
}

@Composable
fun TasaDetalleCard(tasa: TipoCambio) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaTexto = formatoFecha.format(tasa.fecha)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${tasa.monedaBase.codigo} ➔ ${tasa.monedaDestino.codigo}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = fechaTexto,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tasa.monedaDestino.nombre,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "1 ${tasa.monedaBase.codigo} = ${tasa.tasa} ${tasa.monedaDestino.codigo}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ---------------------------------------------------------------------------
// PANTALLA 3: PUBLICACIONES
// ---------------------------------------------------------------------------
@Composable
fun PantallaPublicaciones(publicaciones: List<Publicacion>, cargando: Boolean) {
    if (cargando && publicaciones.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (publicaciones.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontraron publicaciones.")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(publicaciones) { publicacion ->
                PublicacionCard(publicacion = publicacion)
            }
        }
    }
}

@Composable
fun PublicacionCard(publicacion: Publicacion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Autor: ${publicacion.autor.nombre}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = publicacion.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = publicacion.contenido,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val muestraProductos = listOf(
        Producto(1, "Mascara Noir", "Descripción de prueba", 9.99f, "", Categoria(1, "Belleza"))
    )
    val muestraTasas = listOf(
        TipoCambio(1, Date(), Moneda("USD", "Dólar"), Moneda("EUR", "Euro"), 0.95f)
    )
    val muestraPublicaciones = listOf(
        Publicacion(1, "Título de prueba", "Contenido de publicación de prueba", Usuario(1, "Juan Perez"))
    )

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(pestanaSeleccionada = 0, onPestanaSeleccionada = {})
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            PantallaProductos(productos = muestraProductos, cargando = false)
        }
    }
}
