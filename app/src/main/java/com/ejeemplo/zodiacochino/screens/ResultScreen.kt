package com.ejeemplo.zodiacochino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import com.ejeemplo.zodiacochino.R
import com.ejeemplo.zodiacochino.data.ZodiacoRepository
import com.ejeemplo.zodiacochino.model.UserData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    userData: UserData,
    calificacion: Int,
    onBackClicked: () -> Unit
) {
    // Paleta de colores del Zodíaco Chino
    val zodiacColors = listOf(
        Color(0xFFD32F2F),  // Rojo chino
        Color(0xFFFFC107),  // Dorado
        Color(0xFF212121),  // Negro
        Color(0xFFF5F5F5)   // Fondo claro
    )

    val signoChino = userData.obtenerSignoChino()
    val edad = userData.calcularEdad()
    val nombreCompleto = "${userData.nombre} ${userData.apellidoPaterno} ${userData.apellidoMaterno}"

    // Firebase Repository
    val repository = remember { ZodiacoRepository.getInstance() }
    val context = LocalContext.current

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val imagenSigno = when (signoChino.lowercase()) {
        "rata" -> R.drawable.rata
        "buey" -> R.drawable.buey
        "tigre" -> R.drawable.tigre
        "conejo" -> R.drawable.conejo
        "dragón" -> R.drawable.dragon
        "serpiente" -> R.drawable.serpiente
        "caballo" -> R.drawable.caballo
        "cabra" -> R.drawable.cabra
        "mono" -> R.drawable.mono
        "gallo" -> R.drawable.gallo
        "perro" -> R.drawable.perro
        "cerdo" -> R.drawable.cerdo
        else -> R.drawable.ic_launcher_foreground
    }

    // Guardar resultado en Firebase
    LaunchedEffect(Unit) {
        try {
            repository.guardarResultado(
                userData = userData,
                signoChino = signoChino,
                calificacion = calificacion
            )

            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Resultados guardados en la nube",
                    duration = SnackbarDuration.Short
                )
            }
        } catch (e: Exception) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Error al guardar en la nube",
                    duration = SnackbarDuration.Long,
                    actionLabel = "Reintentar"
                ).also { result ->
                    if (result == SnackbarResult.ActionPerformed) {
                        repository.guardarResultado(userData, signoChino, calificacion)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Resultados",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = zodiacColors[2],
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = zodiacColors[2]
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = zodiacColors[3]
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(zodiacColors[3])
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Tarjeta de resultados
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Hola, $nombreCompleto",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = zodiacColors[2],
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Tienes $edad años",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = zodiacColors[2]
                        )
                    )

                    Divider(
                        color = zodiacColors[0].copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Tu signo es",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = zodiacColors[2]
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(zodiacColors[1].copy(alpha = 0.2f), Color.Transparent)
                                ),
                                shape = RoundedCornerShape(70.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = imagenSigno),
                            contentDescription = "Signo $signoChino",
                            modifier = Modifier.size(120.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = signoChino,
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = zodiacColors[0],
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Divider(
                        color = zodiacColors[0].copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Calificación: $calificacion/6",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = if (calificacion >= 4) Color(0xFF388E3C) else zodiacColors[0],
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBackClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = zodiacColors[0],
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    "Volver al Inicio",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}