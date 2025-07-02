package com.ejeemplo.zodiacochino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ejeemplo.zodiacochino.model.UserData
import com.ejeemplo.zodiacochino.navigation.Screens
import com.ejeemplo.zodiacochino.screens.ExamScreen
import com.ejeemplo.zodiacochino.screens.FormScreen
import com.ejeemplo.zodiacochino.screens.ResultsScreen
import com.ejeemplo.zodiacochino.screens.SexScreen
import com.ejeemplo.zodiacochino.ui.theme.ZodiacoChinoTheme
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZodiacoChinoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ZodiacoApp()
                }
            }
        }
    }
}

@Composable
fun ZodiacoApp() {
    val navController = rememberNavController()
    val gson = Gson()

    NavHost(
        navController = navController,
        startDestination = Screens.FormScreen.route
    ) {
        // Pantalla 1: Formulario de datos personales
        composable(Screens.FormScreen.route) {
            FormScreen(
                onNextClicked = { partialUserData ->
                    val userJson = gson.toJson(partialUserData)
                    val encodedUserJson = URLEncoder.encode(
                        userJson,
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate(Screens.SexoScreen.createRoute(encodedUserJson))
                },
                onCleanClicked = {
                    // Acción para limpiar el formulario
                }
            )
        }

        // Pantalla 2: Selección de sexo
        composable(
            route = "${Screens.SexoScreen.route}/{userData}",
            arguments = listOf(navArgument("userData") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("userData") ?: ""
            val userJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val partialUserData = gson.fromJson(userJson, UserData::class.java)

            SexScreen(
                partialUserData = partialUserData,
                onNextClicked = { completeUserData ->
                    val userJson = gson.toJson(completeUserData)
                    val encodedUserJson = URLEncoder.encode(
                        userJson,
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate(Screens.ExamScreen.createRoute(encodedUserJson))
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla 3: Examen
        composable(
            route = "${Screens.ExamScreen.route}/{userData}",
            arguments = listOf(navArgument("userData") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("userData") ?: ""
            val userJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val userData = gson.fromJson(userJson, UserData::class.java)

            ExamScreen(
                userData = userData,
                onFinishClicked = { calificacion ->
                    val reEncodedJson = URLEncoder.encode(
                        gson.toJson(userData),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate("${Screens.ResultsScreen.route}/$reEncodedJson/$calificacion") {
                        popUpTo(Screens.FormScreen.route) { inclusive = false }
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla 4: Resultados
        composable(
            route = "${Screens.ResultsScreen.route}/{userData}/{calificacion}",
            arguments = listOf(
                navArgument("userData") { type = NavType.StringType },
                navArgument("calificacion") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("userData") ?: ""
            val userJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val calificacion = backStackEntry.arguments?.getInt("calificacion") ?: 0
            val userData = gson.fromJson(userJson, UserData::class.java)

            ResultsScreen(
                userData = userData,
                calificacion = calificacion,
                onBackClicked = {
                    navController.popBackStack(Screens.FormScreen.route, false)
                }
            )
        }
    }
}