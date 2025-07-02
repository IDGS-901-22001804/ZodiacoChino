package com.ejeemplo.zodiacochino.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ejeemplo.zodiacochino.model.UserData

@Composable
fun FormScreen(
    onNextClicked: (UserData) -> Unit,
    onCleanClicked: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var año by remember { mutableStateOf("") }
    var errorDia by remember { mutableStateOf<String?>(null) }
    var errorMes by remember { mutableStateOf<String?>(null) }

    // Paleta de colores del Zodíaco Chino
    val zodiacColors = listOf(
        Color(0xFFD32F2F),  // Rojo chino
        Color(0xFFFFC107),  // Dorado
        Color(0xFF212121)   // Negro
    )

    // Funciones de validación
    fun validarDia(diaStr: String): Boolean {
        return diaStr.toIntOrNull()?.let { it in 1..31 } ?: false
    }

    fun validarMes(mesStr: String): Boolean {
        return mesStr.toIntOrNull()?.let { it in 1..12 } ?: false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "☯",
                style = MaterialTheme.typography.displaySmall,
                color = zodiacColors[0],
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Zodíaco Chino",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = zodiacColors[2],
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Campos de texto
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = zodiacColors[2]) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = zodiacColors[0],
                unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f),
                cursorColor = zodiacColors[0]
            ),
            leadingIcon = {
                Icon(Icons.Default.Person, null, tint = zodiacColors[0])
            },
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = apellidoPaterno,
            onValueChange = { apellidoPaterno = it },
            label = { Text("Apellido Paterno", color = zodiacColors[2]) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = zodiacColors[0],
                unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = apellidoMaterno,
            onValueChange = { apellidoMaterno = it },
            label = { Text("Apellido Materno", color = zodiacColors[2]) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = zodiacColors[0],
                unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Sección de fecha
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(Icons.Default.DateRange, null, tint = zodiacColors[0], modifier = Modifier.width(24.dp))
            Text(
                text = " Fecha de Nacimiento",
                style = MaterialTheme.typography.labelLarge.copy(color = zodiacColors[2]),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Campo Día (1-31)
            OutlinedTextField(
                value = dia,
                onValueChange = {
                    if (it.length <= 2 && (it.isEmpty() || it.toIntOrNull() != null)) {
                        dia = it
                        errorDia = if (it.isNotEmpty() && !validarDia(it)) "Día inválido (1-31)" else null
                    }
                },
                label = { Text("Día", color = zodiacColors[2]) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorDia != null,
                supportingText = { errorDia?.let { Text(it, color = Color.Red) } },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = zodiacColors[0],
                    unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Campo Mes (01-12)
            OutlinedTextField(
                value = mes,
                onValueChange = {
                    if (it.length <= 2 && (it.isEmpty() || it.toIntOrNull() != null)) {
                        mes = it
                        errorMes = if (it.isNotEmpty() && !validarMes(it)) "Mes inválido (01-12)" else null
                    }
                },
                label = { Text("Mes", color = zodiacColors[2]) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorMes != null,
                supportingText = { errorMes?.let { Text(it, color = Color.Red) } },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = zodiacColors[0],
                    unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Campo Año
            OutlinedTextField(
                value = año,
                onValueChange = { if (it.length <= 4 && (it.isEmpty() || it.toIntOrNull() != null)) año = it },
                label = { Text("Año", color = zodiacColors[2]) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = zodiacColors[0],
                    unfocusedBorderColor = zodiacColors[2].copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    nombre = ""
                    apellidoPaterno = ""
                    apellidoMaterno = ""
                    dia = ""
                    mes = ""
                    año = ""
                    errorDia = null
                    errorMes = null
                    onCleanClicked()
                },
                border = BorderStroke(1.dp, zodiacColors[0]),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Limpiar", color = zodiacColors[0])
            }

            Button(
                onClick = {
                    // Validar campos antes de continuar
                    val diaValido = validarDia(dia)
                    val mesValido = validarMes(mes)

                    if (!diaValido) errorDia = "Ingrese día válido (1-31)"
                    if (!mesValido) errorMes = "Ingrese mes válido (01-12)"

                    if (nombre.isNotBlank() && apellidoPaterno.isNotBlank() &&
                        diaValido && mesValido && año.isNotBlank()) {
                        val userData = UserData(
                            nombre = nombre,
                            apellidoPaterno = apellidoPaterno,
                            apellidoMaterno = apellidoMaterno,
                            diaNacimiento = dia.toInt(),
                            mesNacimiento = mes.toInt(),
                            añoNacimiento = año.toInt(),
                            sexo = "" // Se completará en la siguiente pantalla
                        )
                        onNextClicked(userData)
                    }
                },
                enabled = nombre.isNotBlank() && apellidoPaterno.isNotBlank() &&
                        dia.isNotBlank() && mes.isNotBlank() && año.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = zodiacColors[0],
                    disabledContainerColor = zodiacColors[0].copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Siguiente", color = Color.White)
            }
        }
    }
}