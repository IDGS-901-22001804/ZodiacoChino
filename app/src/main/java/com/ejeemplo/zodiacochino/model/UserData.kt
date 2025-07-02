package com.ejeemplo.zodiacochino.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class UserData(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val diaNacimiento: Int,
    val mesNacimiento: Int,
    val añoNacimiento: Int,
    val sexo: String
) : Parcelable {
    fun calcularEdad(): Int {
        val calendar = Calendar.getInstance()
        val añoActual = calendar.get(Calendar.YEAR)
        val mesActual = calendar.get(Calendar.MONTH) + 1
        val diaActual = calendar.get(Calendar.DAY_OF_MONTH)

        var edad = añoActual - añoNacimiento

        if (mesNacimiento > mesActual || (mesNacimiento == mesActual && diaNacimiento > diaActual)) {
            edad--
        }

        return edad
    }

    fun obtenerSignoChino(): String {
        val signos = listOf(
            "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
            "Caballo", "Cabra", "Mono", "Gallo", "Perro", "Cerdo"
        )
        return signos[(añoNacimiento - 1900) % 12]
    }
}