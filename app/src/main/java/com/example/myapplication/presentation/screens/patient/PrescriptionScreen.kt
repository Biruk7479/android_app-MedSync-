package com.example.myapplication.presentation.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun PrescriptionScreen(navController: NavHostController, authPreferences: AuthPreferences) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )

    val prescriptions = listOf(
        Prescription("Dr. John Smith", "Aspirin", "Take 1 tablet daily"),
        Prescription("Dr. Sarah Johnson", "Ibuprofen", "Take 2 tablets as needed")
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController , authPreferences) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Prescriptions",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(16.dp))
            prescriptions.forEach { prescription ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Doctor: ${prescription.doctorName}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = rubikFontFamily
                        )
                        Text(
                            text = "Medication: ${prescription.medication}",
                            fontSize = 16.sp,
                            fontFamily = rubikFontFamily
                        )
                        Text(
                            text = "Instructions: ${prescription.instructions}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = rubikFontFamily
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

data class Prescription(val doctorName: String, val medication: String, val instructions: String)