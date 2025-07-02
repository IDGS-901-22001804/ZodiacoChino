package com.ejeemplo.zodiacochino.data

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ejeemplo.zodiacochino.model.UserData

class ZodiacoRepository {
    private val database: DatabaseReference by lazy {
        Log.d("FirebaseInit", "Inicializando referencia a la base de datos")
        Firebase.database.reference
    }

    fun guardarResultado(userData: UserData, signoChino: String, calificacion: Int) {
        Log.d("Firebase", "Intentando guardar datos en Firebase")

        try {
            val resultado = hashMapOf(
                "nombre" to userData.nombre,
                "apellidoPaterno" to userData.apellidoPaterno,
                "apellidoMaterno" to userData.apellidoMaterno,
                "diaNacimiento" to userData.diaNacimiento,
                "mesNacimiento" to userData.mesNacimiento,
                "añoNacimiento" to userData.añoNacimiento,
                "sexo" to userData.sexo,
                "signoChino" to signoChino,
                "calificacion" to calificacion,
                "fechaRegistro" to System.currentTimeMillis()
            )

            Log.d("Firebase", "Datos a guardar: $resultado")

            database.child("resultados").push().setValue(resultado)
                .addOnSuccessListener {
                    Log.d("Firebase", "✅ Datos guardados correctamente en Firebase")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "❌ Error al guardar datos", e)
                }
                .addOnCompleteListener {
                    Log.d("Firebase", "Operación completada: ${it.isSuccessful}")
                }
        } catch (e: Exception) {
            Log.e("Firebase", "🔥 Error general en guardarResultado", e)
        }
    }

    companion object {
        @Volatile private var INSTANCE: ZodiacoRepository? = null

        fun getInstance(): ZodiacoRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ZodiacoRepository().also {
                    Log.d("Firebase", "Creando nueva instancia de ZodiacoRepository")
                    INSTANCE = it
                }
            }
        }
    }
}