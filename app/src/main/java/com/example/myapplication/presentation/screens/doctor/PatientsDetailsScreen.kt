package com.example.myapplication.presentation.screens.doctor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.model.doctor.MedicalHistory
import com.example.myapplication.data.model.doctor.Prescription
import com.example.myapplication.data.remote.NetworkProvider
import com.example.myapplication.data.repository.doctor.DoctorRepository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PatientDetailsScreen(
    navController: NavHostController,
    patientId: String,
    authPreferences: AuthPreferences
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val doctorApi = NetworkProvider.doctorApi
    val viewModel: PatientDetailsViewModel = viewModel(
        factory = PatientDetailsViewModelFactory(DoctorRepository(authPreferences, doctorApi))
    )

    LaunchedEffect(patientId) {
        viewModel.fetchPatientDetails(patientId)
    }

    val patientState by viewModel.patientState.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD8C4E7))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Patient Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = rubikFontFamily,
                    color = Color.Black
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD8C4E7))
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (val state = patientState) {
                is PatientDetailsViewModel.PatientState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is PatientDetailsViewModel.PatientState.Success -> {
                    val patientData = state.patientDetails.data
                    patientData?.let { data ->
                        // Patient Info
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_patient),
                                    contentDescription = "Patient",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = data.patient.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = rubikFontFamily
                                )
                                Text(
                                    text = "Email: ${data.patient.email}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontFamily = rubikFontFamily
                                )
                                Text(
                                    text = "DOB: ${formatDate(data.patient.dateOfBirth)}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontFamily = rubikFontFamily
                                )
                                Text(
                                    text = "Gender: ${data.patient.gender}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontFamily = rubikFontFamily
                                )
                                Text(
                                    text = "Blood Group: ${data.patient.bloodGroup}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontFamily = rubikFontFamily
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Medical History
                        MedicalHistorySection(data.medicalHistory, rubikFontFamily)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Prescriptions
                        PrescriptionSection(data.prescriptions, rubikFontFamily)
                    }
                }
                is PatientDetailsViewModel.PatientState.Error -> {
                    Text(
                        text = state.message,
                        fontFamily = rubikFontFamily,
                        fontSize = 16.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MedicalHistorySection(medicalHistory: MedicalHistory?, rubikFontFamily: FontFamily) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Medical History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (medicalHistory == null || (medicalHistory.allergies.isNullOrEmpty() && medicalHistory.conditions.isNullOrEmpty() && medicalHistory.pastSurgeries.isNullOrEmpty())) {
                Text(
                    text = "No medical history available",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
            } else {
                medicalHistory.allergies?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = "Allergies: ${it.joinToString(", ")}",
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                }
                medicalHistory.conditions?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = "Conditions: ${it.joinToString(", ")}",
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                }
                medicalHistory.pastSurgeries?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = "Past Surgeries: ${it.joinToString(", ")}",
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun PrescriptionSection(prescriptions: List<Prescription>, rubikFontFamily: FontFamily) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Prescriptions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (prescriptions.isEmpty()) {
                Text(
                    text = "No prescriptions available",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
            } else {
                LazyColumn {
                    itemsIndexed(prescriptions) { _, prescription ->
                        Column {
                            Text(
                                text = "Medication: ${prescription.medication ?: "N/A"}",
                                fontSize = 14.sp,
                                fontFamily = rubikFontFamily
                            )
                            Text(
                                text = "Dosage: ${prescription.dosage ?: "N/A"}",
                                fontSize = 14.sp,
                                fontFamily = rubikFontFamily
                            )
                            Text(
                                text = "Date: ${prescription.date?.let { formatDate(it) } ?: "N/A"}",
                                fontSize = 14.sp,
                                fontFamily = rubikFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

fun formatDate(dateString: String?): String {
    if (dateString == null) return "N/A"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "N/A"
    } catch (e: Exception) {
        "N/A"
    }
}