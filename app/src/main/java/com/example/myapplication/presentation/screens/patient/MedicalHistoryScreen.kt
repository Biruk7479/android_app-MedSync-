package com.example.myapplication.presentation.screens.patient

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.remote.NetworkProvider
import com.example.myapplication.data.repository.patient.PatientRepository
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun MedicalHistoryScreen(
    navController: NavHostController,
    authPreferences: AuthPreferences
) {
    val TAG = "MedicalHistoryScreen"
    Log.d(TAG, "Composing MedicalHistoryScreen")
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val patientApi = NetworkProvider.patientApi
    val viewModel: MedicalHistoryViewModel = viewModel(
        factory = MedicalHistoryViewModelFactory(PatientRepository(authPreferences, patientApi))
    )
    val medicalRecordState by viewModel.medicalRecordState.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, authPreferences) }
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
                text = "Medical History",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = medicalRecordState) {
                is MedicalHistoryViewModel.MedicalRecordState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is MedicalHistoryViewModel.MedicalRecordState.Success -> {
                    if (state.records.isEmpty()) {
                        Text(
                            text = "No medical records found",
                            fontSize = 16.sp,
                            fontFamily = rubikFontFamily,
                            color = Color.Gray
                        )
                    } else {
                        state.records.forEach { record ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Doctor: ${record.doctorInfo?.name ?: "N/A"}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = rubikFontFamily
                                    )
                                    Text(
                                        text = "Diagnosis: ${record.diagnosis ?: "N/A"}",
                                        fontSize = 16.sp,
                                        fontFamily = rubikFontFamily
                                    )
                                    Text(
                                        text = "Treatment: ${record.treatment ?: "N/A"}",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        fontFamily = rubikFontFamily
                                    )
                                    Text(
                                        text = "Last Updated: ${record.lastUpdated ?: "N/A"}",
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
                is MedicalHistoryViewModel.MedicalRecordState.Error -> {
                    Text(
                        text = state.message,
                        fontSize = 16.sp,
                        fontFamily = rubikFontFamily,
                        color = Color.Red
                    )
                }
            }
        }
    }
}