package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.SystemUiController
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.presentation.auth.LoginScreen
import com.example.myapplication.presentation.auth.LoginViewModel
import com.example.myapplication.presentation.auth.LoginViewModelFactory
import com.example.myapplication.presentation.auth.RegisterScreen
import com.example.myapplication.presentation.screens.admin.AdminDashboardScreen
import com.example.myapplication.presentation.screens.intro.IntroScreen
import com.example.myapplication.presentation.screens.patient.AppointmentBookingScreen
import com.example.myapplication.presentation.screens.patient.DoctorChatScreen
import com.example.myapplication.presentation.screens.patient.DoctorDetailScreen
import com.example.myapplication.presentation.screens.patient.DoctorsScreen
import com.example.myapplication.presentation.screens.patient.MedicalHistoryScreen
import com.example.myapplication.presentation.screens.patient.NotificationScreen
import com.example.myapplication.presentation.screens.patient.PatientDashboardScreen
import com.example.myapplication.presentation.screens.patient.PrescriptionScreen
import com.example.myapplication.presentation.screens.splash.SplashScreen
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun AppNavHost() {
    SystemUiController()
    val navController = rememberNavController()
    val context = LocalContext.current
    val authPreferences = remember { AuthPreferences(context) }
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(AuthRepository(), authPreferences)
    )

    LaunchedEffect(Unit) {
        if (authPreferences.isLoggedIn()) {
            val name = authPreferences.getName() ?: "Guest"
            val encodedName = URLEncoder.encode(name, "UTF-8")
            val role = authPreferences.getRole() ?: "patient"
            val destination = when (role.lowercase()) {
                "patient" -> "patient_dashboard/$encodedName"
                "doctor" -> "doctor_dashboard/$encodedName"
                "triage" -> "triage_dashboard/$encodedName"
                "admin" -> "admin_dashboard/$encodedName"
                else -> "patient_dashboard/$encodedName"
            }
            navController.navigate(destination) {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("intro1") {
            IntroScreen(navController, screenNumber = 0)
        }
        composable("intro2") {
            IntroScreen(navController, screenNumber = 1)
        }
        composable("intro3") {
            IntroScreen(navController, screenNumber = 2)
        }
        composable("login") {
            LoginScreen(navController, loginViewModel, authPreferences)
        }
        composable("register") {
            RegisterScreen(navController, authPreferences)
        }
        composable(
            route = "patient_dashboard/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")?.let { URLDecoder.decode(it, "UTF-8") } ?: "Guest"
            PatientDashboardScreen(navController, name, authPreferences)
        }
        composable("appointment_booking") {
            AppointmentBookingScreen(navController)
        }
        composable("doctors") {
            DoctorsScreen(navController)
        }
        composable(
            "doctor_detail/{doctorName}",
            arguments = listOf(navArgument("doctorName") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorName = backStackEntry.arguments?.getString("doctorName") ?: ""
            DoctorDetailScreen(navController, doctorName)
        }
        composable(
            route = "admin_dashboard/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")?.let { URLDecoder.decode(it, "UTF-8") } ?: "Guest"
            AdminDashboardScreen(navController, name, authPreferences)
        }
        composable("medical_history") {
            MedicalHistoryScreen(navController)
        }
        composable("prescriptions") {
            PrescriptionScreen(navController)
        }
        composable("doctor_chat") {
            DoctorChatScreen(navController)
        }
        composable("notifications") {
            NotificationScreen(navController) { navController.popBackStack() }
        }
    }
}