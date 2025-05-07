package com.example.myapplication.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.SystemUiController
import com.example.myapplication.presentation.auth.LoginScreen
import com.example.myapplication.presentation.auth.RegisterScreen
import com.example.myapplication.presentation.screens.splash.SplashScreen
import com.example.myapplication.presentation.screens.intro.IntroScreen
import com.example.myapplication.presentation.screens.patient.AppointmentBookingScreen
import com.example.myapplication.presentation.screens.patient.DoctorChatScreen
import com.example.myapplication.presentation.screens.patient.DoctorDetailScreen
import com.example.myapplication.presentation.screens.patient.DoctorsScreen
import com.example.myapplication.presentation.screens.patient.MedicalHistoryScreen
import com.example.myapplication.presentation.screens.patient.NotificationScreen
import com.example.myapplication.presentation.screens.patient.PatientDashboardScreen
import com.example.myapplication.presentation.screens.patient.PrescriptionScreen


@Composable
fun AppNavHost() {
    SystemUiController()
    val navController = rememberNavController()

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
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("patient_dashboard")
        {
            PatientDashboardScreen(navController)
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
