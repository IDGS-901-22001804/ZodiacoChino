package com.ejeemplo.zodiacochino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ejeemplo.zodiacochino.model.UserData

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    userData: UserData,
    onFinishClicked: (Int) -> Unit,
    onBackClicked: () -> Unit
) {
    // Paleta de colores del Zodíaco Chino
    val zodiacColors = listOf(
        Color(0xFFD32F2F),  // Rojo chino
        Color(0xFFFFC107),  // Dorado
        Color(0xFF212121),  // Negro
        Color(0xFFF5F5F5)   // Fondo claro
    )

    val preguntas = listOf(
        Pregunta(
            texto = "¿Cuál es la suma de 2 + 2?",
            opciones = listOf("8", "6", "4", "3"),
            respuestaCorrecta = 2
        ),
        Pregunta(
            texto = "¿En qué año cayó el Muro de Berlín?",
            opciones = listOf("1985", "1989", "1991", "1978"),
            respuestaCorrecta = 1
        ),
        Pregunta(
            texto = "¿Quién escribió 'Cien años de soledad'?",
            opciones = listOf("Julio Cortázar", "Gabriel García Márquez", "Mario Vargas Llosa", "Pablo Neruda"),
            respuestaCorrecta = 1
        ),
        Pregunta(
            texto = "¿Cuál es el elemento químico con símbolo 'Au'?",
            opciones = listOf("Plata", "Oro", "Aluminio", "Argón"),
            respuestaCorrecta = 1
        ),
        Pregunta(
            texto = "¿Qué pintor es conocido por cortarse la oreja?",
            opciones = listOf("Pablo Picasso", "Salvador Dalí", "Vincent van Gogh", "Claude Monet"),
            respuestaCorrecta = 2
        ),
        Pregunta(
            texto = "¿Cuál es el río más largo del mundo?",
            opciones = listOf("Nilo", "Amazonas", "Yangtsé", "Misisipi"),
            respuestaCorrecta = 1
        )
    )

    val respuestasSeleccionadas = remember { mutableStateMapOf<Int, Int>() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Examen de Cultura General",
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(zodiacColors[3])
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Demuestra tus conocimientos",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = zodiacColors[2],
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(preguntas) { index, pregunta ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${index + 1}. ${pregunta.texto}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = zodiacColors[2],
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Divider(
                                color = zodiacColors[0].copy(alpha = 0.3f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            pregunta.opciones.forEachIndexed { opcionIndex, opcion ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = respuestasSeleccionadas[index] == opcionIndex,
                                        onClick = { respuestasSeleccionadas[index] = opcionIndex },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = zodiacColors[0],
                                            unselectedColor = zodiacColors[2]
                                        )
                                    )
                                    Text(
                                        text = opcion,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = zodiacColors[2]
                                        ),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val calificacion = preguntas.indices.count { index ->
                        respuestasSeleccionadas[index] == preguntas[index].respuestaCorrecta
                    }
                    onFinishClicked(calificacion)
                },
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
                ),
                enabled = respuestasSeleccionadas.size == preguntas.size
            ) {
                Text(
                    "Terminar Examen",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

