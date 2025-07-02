package com.ejeemplo.zodiacochino.navigation

sealed class Screens(val route: String) {
    object FormScreen : Screens("form_screen")
    object SexoScreen : Screens("sex_screen") {  // Nombre consistente
        fun createRoute(userData: String) = "$route/$userData"
    }
    object ExamScreen : Screens("exam_screen") {
        fun createRoute(userData: String) = "$route/$userData"
    }
    object ResultsScreen : Screens("results_screen") {
        fun createRoute(userData: String, calificacion: Int) = "$route/$userData/$calificacion"
    }
}