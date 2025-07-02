package com.ejeemplo.zodiacochino.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ejeemplo.zodiacochino.model.UserData
import androidx.compose.ui.text.font.FontWeight
import com.ejeemplo.zodiacochino.navigation.Screens

@Composable
fun SexScreen(
    partialUserData: UserData,
    onNextClicked: (UserData) -> Unit,
    onBackClicked: () -> Unit
) {
    var sexo by remember { mutableStateOf("") }

    // Paleta de colores del Zodíaco Chino
    val zodiacColors = listOf(
        Color(0xFFD32F2F),  // Rojo chino
        Color(0xFFFFC107),  // Dorado
        Color(0xFF212121)   // Negro
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Selecciona tu sexo",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = zodiacColors[2],
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de sexo
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sexo == "Masculino",
                    onClick = { sexo = "Masculino" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = zodiacColors[0],
                        unselectedColor = zodiacColors[2]
                    )
                )
                Text(
                    "Masculino",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = zodiacColors[2]
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = sexo == "Femenino",
                    onClick = { sexo = "Femenino" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = zodiacColors[0],
                        unselectedColor = zodiacColors[2]
                    )
                )
                Text(
                    "Femenino",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = zodiacColors[2]
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onBackClicked,
                border = BorderStroke(1.dp, zodiacColors[0]),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Atrás",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = zodiacColors[0]
                    )
                )
            }

            Button(
                onClick = {
                    val completeUserData = partialUserData.copy(sexo = sexo)
                    onNextClicked(completeUserData)
                },
                enabled = sexo.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = zodiacColors[0],
                    disabledContainerColor = zodiacColors[0].copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Continuar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    )
                )
            }
        }
    }
}