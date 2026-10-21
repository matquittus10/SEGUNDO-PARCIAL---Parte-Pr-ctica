package com.example.segundoparcial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.segundoparcial.data.Repository
import com.example.segundoparcial.data.local.AppDatabase
import com.example.segundoparcial.data.local.ElementoEntity
import com.example.segundoparcial.data.remote.RetrofitClient
import com.example.segundoparcial.ui.MainViewModel
import com.example.segundoparcial.ui.theme.SegundoParcialTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Inicialización de base de datos local y cliente API
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = Repository(
            dao = database.elementoDao(),
            api = RetrofitClient.api
        )

        // 2. Creación del ViewModel utilizando su Factory
        val viewModel: MainViewModel by viewModels {
            MainViewModel.Factory(repository)
        }

        setContent {
            SegundoParcialTheme {
                PantallaPrincipal(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(viewModel: MainViewModel) {
    val elementos by viewModel.elementosState.collectAsState()
    val estadoOperacion by viewModel.estadoOperacion.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Examen Segundo Parcial") },
                actions = {
                    IconButton(onClick = { viewModel.sincronizarConApi() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sincronizar con API"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val nuevoId = (elementos.maxOfOrNull { it.id } ?: 0) + 1
                val nuevoElemento = ElementoEntity(
                    id = nuevoId,
                    titulo = "Elemento $nuevoId",
                    descripcion = "Elemento guardado en Room localmente",
                    precio = 25.0
                )
                viewModel.agregarElementoLocal(nuevoElemento)
                Toast.makeText(context, "Guardado en Room", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar elemento"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            estadoOperacion?.let { mensaje ->
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (elementos.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No hay elementos en la base de datos local.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.sincronizarConApi() }) {
                        Text("Descargar de API")
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = elementos,
                        key = { item -> item.id }
                    ) { elemento ->
                        ItemCard(
                            elemento = elemento,
                            onEliminar = { viewModel.eliminarElementoLocal(elemento) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(elemento: ElementoEntity, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${elemento.id}. ${elemento.titulo}",
                    style = MaterialTheme.typography.titleMedium
                )
                if (elemento.descripcion.isNotEmpty()) {
                    Text(
                        text = elemento.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (elemento.precio > 0) {
                    Text(
                        text = "Precio: $${elemento.precio}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar de Room",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
