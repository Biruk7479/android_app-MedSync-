//package com.example.myapplication.presentation.screens.doctor
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.myapplication.R
//import com.example.myapplication.data.model.AuthPreferences
//import com.example.myapplication.data.remote.NetworkProvider
//import com.example.myapplication.data.repository.doctor.DoctorRepository
//import com.example.myapplication.navigation.DoctorBottomNavBar
//
//@Composable
//fun PatientDetailsScreen(
//    navController: NavHostController,
//    patientId: String,
//    authPreferences: AuthPreferences
//) {
//    val TAG = "PatientDetailsScreen"
//    Log.d(TAG, "Composing PatientDetailsScreen for patientId: $patientId")
//    val rubikFontFamily = FontFamily(
//        Font(R.font.rubik_regular, FontWeight.Normal),
//        Font(R.font.rubik_medium, FontWeight.Medium),
//        Font(R.font.rubik_bold, FontWeight.Bold)
//    )
//    val doctorApi = NetworkProvider.doctorApi
//    val viewModel: PatientDetailsViewModel = viewModel(
//        factory = PatientDetailsViewModelFactory(DoctorRepository(authPreferences, doctorApi), patientId)
//    )
//    Log.d(TAG, "ViewModel initialized: $viewModel")
//
//    val patientDetailsState by viewModel.patientDetailsState.collectAsState()
//    var showCreateDialog by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFD8C4E7))
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.bell),
//                    contentDescription = "Back",
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clickable { navController.navigateUp() },
//                    tint = Color.Black
//                )
//                Spacer(modifier = Modifier.width(16.dp))
//                Text(
//                    text = "Patient Details",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = rubikFontFamily,
//                    color = Color.Black
//                )
//            }
//        },
//        bottomBar = {
//            DoctorBottomNavBar(
//                navController = navController,
//                authPreferences = authPreferences,
//                currentRoute = "patient_details"
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFD8C4E7))
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//            when (val state = patientDetailsState) {
//                is PatientDetailsViewModel.PatientDetailsState.Loading -> {
//                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//                }
//                is PatientDetailsViewModel.PatientDetailsState.Success -> {
//                    val patient = state.patient
//                    val medicalRecords = state.medicalRecords
//
//                    Card(
//                        shape = RoundedCornerShape(16.dp),
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//                    ) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(
//                                text = "Name: ${patient.name}",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Bold,
//                                fontFamily = rubikFontFamily
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "Email: ${patient.email}",
//                                fontSize = 16.sp,
//                                fontFamily = rubikFontFamily
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "Date of Birth: ${patient.dateOfBirth}",
//                                fontSize = 16.sp,
//                                fontFamily = rubikFontFamily
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "Gender: ${patient.gender ?: "N/A"}",
//                                fontSize = 16.sp,
//                                fontFamily = rubikFontFamily
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "Blood Group: ${patient.bloodGroup}",
//                                fontSize = 16.sp,
//                                fontFamily = rubikFontFamily
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(
//                        text = "Medical Records",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = rubikFontFamily
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    LazyColumn {
//                        items(medicalRecords) { record ->
//                            Card(
//                                shape = RoundedCornerShape(8.dp),
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(vertical = 4.dp)
//                                    .clickable {
//                                        record.recordId?.let { id ->
//                                            navController.navigate("medical_record_details/$id")
//                                        }
//                                    },
//                                colors = CardDefaults.cardColors(containerColor = Color.White)
//                            ) {
//                                Column(modifier = Modifier.padding(8.dp)) {
//                                    Text(
//                                        text = "Diagnosis: ${record.diagnosis ?: "N/A"}",
//                                        fontSize = 16.sp,
//                                        fontFamily = rubikFontFamily
//                                    )
//                                    Text(
//                                        text = "Doctor: ${record.doctorInfo.name}",
//                                        fontSize = 14.sp,
//                                        color = Color.Gray,
//                                        fontFamily = rubikFontFamily
//                                    )
//                                    Text(
//                                        text = "Last Updated: ${record.lastUpdated ?: "N/A"}",
//                                        fontSize = 12.sp,
//                                        color = Color.Gray,
//                                        fontFamily = rubikFontFamily
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = { showCreateDialog = true },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally)
//                            .padding(8.dp)
//                    ) {
//                        Text("Add Medical Record", color = Color.White, fontFamily = rubikFontFamily)
//                    }
//                }
//                is PatientDetailsViewModel.PatientDetailsState.Error -> {
//                    Text(
//                        text = state.message,
//                        fontFamily = rubikFontFamily,
//                        fontSize = 16.sp,
//                        color = Color.Red,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//            }
//
//            if (showCreateDialog) {
//                CreateMedicalRecordDialog(
//                    onDismiss = { showCreateDialog = false },
//                    onSubmit = { diagnosis, treatment, notes ->
//                        viewModel.createMedicalRecord(diagnosis, treatment, notes)
//                        showCreateDialog = false
//                    },
//                    rubikFontFamily = rubikFontFamily
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun CreateMedicalRecordDialog(
//    onDismiss: () -> Unit,
//    onSubmit: (String, String, String) -> Unit,
//    rubikFontFamily: FontFamily
//) {
//    var diagnosis by remember { mutableStateOf(TextFieldValue("")) }
//    var treatment by remember { mutableStateOf(TextFieldValue("")) }
//    var notes by remember { mutableStateOf(TextFieldValue("")) }
//
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            modifier = Modifier
//                .padding(16.dp)
//                .width(300.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(24.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Add Medical Record",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = rubikFontFamily,
//                    modifier = Modifier.padding(bottom = 16.dp),
//                    color = Color(0xFF4CAF50)
//                )
//
//                OutlinedTextField(
//                    value = diagnosis,
//                    onValueChange = { diagnosis = it },
//                    label = { Text("Diagnosis", fontFamily = rubikFontFamily) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF4CAF50),
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                OutlinedTextField(
//                    value = treatment,
//                    onValueChange = { treatment = it },
//                    label = { Text("Treatment", fontFamily = rubikFontFamily) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF4CAF50),
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                OutlinedTextField(
//                    value = notes,
//                    onValueChange = { notes = it },
//                    label = { Text("Notes", fontFamily = rubikFontFamily) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF4CAF50),
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Button(
//                        onClick = onDismiss,
//                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Cancel", color = Color.White, fontFamily = rubikFontFamily)
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(
//                        onClick = {
//                            onSubmit(diagnosis.text, treatment.text, notes.text)
//
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Submit", color = Color.White, fontFamily = rubikFontFamily)
//                    }
//
//                }
//            }
//        }
//    }
//}

package com.example.myapplication.presentation.screens.doctor

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.remote.NetworkProvider
import com.example.myapplication.data.repository.doctor.DoctorRepository
import com.example.myapplication.navigation.DoctorBottomNavBar

@Composable
fun PatientDetailsScreen(
    navController: NavHostController,
    patientId: String,
    authPreferences: AuthPreferences
) {
    val TAG = "PatientDetailsScreen"
    Log.d(TAG, "Composing PatientDetailsScreen for patientId: $patientId")
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val doctorApi = NetworkProvider.doctorApi
    val viewModel: PatientDetailsViewModel = viewModel(
        factory = PatientDetailsViewModelFactory(DoctorRepository(authPreferences, doctorApi), patientId)
    )
    Log.d(TAG, "ViewModel initialized: $viewModel")

    val patientDetailsState by viewModel.patientDetailsState.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccessMessage by remember { mutableStateOf(false) }

    // Show snackbar and navigate when showSuccessMessage is true
    LaunchedEffect(showSuccessMessage) {
        if (showSuccessMessage) {
            snackbarHostState.showSnackbar("Successful")
            navController.navigate("doctor_dashboard") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            showSuccessMessage = false
        }
    }

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
                        .clickable { navController.navigateUp() },
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
        },
        bottomBar = {
            DoctorBottomNavBar(
                navController = navController,
                authPreferences = authPreferences,
                currentRoute = "patient_details"
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD8C4E7))
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (val state = patientDetailsState) {
                is PatientDetailsViewModel.PatientDetailsState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is PatientDetailsViewModel.PatientDetailsState.Success -> {
                    val patient = state.patient
                    val medicalRecords = state.medicalRecords

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Name: ${patient.name}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = rubikFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Email: ${patient.email}",
                                fontSize = 16.sp,
                                fontFamily = rubikFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Date of Birth: ${patient.dateOfBirth}",
                                fontSize = 16.sp,
                                fontFamily = rubikFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Gender: ${patient.gender ?: "N/A"}",
                                fontSize = 16.sp,
                                fontFamily = rubikFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Blood Group: ${patient.bloodGroup}",
                                fontSize = 16.sp,
                                fontFamily = rubikFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Medical Records",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rubikFontFamily
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn {
                        items(medicalRecords) { record ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        record.recordId?.let { id ->
                                            navController.navigate("medical_record_details/$id")
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Diagnosis: ${record.diagnosis ?: "N/A"}",
                                        fontSize = 16.sp,
                                        fontFamily = rubikFontFamily
                                    )
                                    Text(
                                        text = "Doctor: ${record.doctorInfo.name}",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        fontFamily = rubikFontFamily
                                    )
                                    Text(
                                        text = "Last Updated: ${record.lastUpdated ?: "N/A"}",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        fontFamily = rubikFontFamily
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showCreateDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    ) {
                        Text("Add Medical Record", color = Color.White, fontFamily = rubikFontFamily)
                    }
                }
                is PatientDetailsViewModel.PatientDetailsState.Error -> {
                    Text(
                        text = state.message,
                        fontFamily = rubikFontFamily,
                        fontSize = 16.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (showCreateDialog) {
                CreateMedicalRecordDialog(
                    onDismiss = { showCreateDialog = false },
                    onSubmit = { diagnosis, treatment, notes ->
                        viewModel.createMedicalRecord(diagnosis, treatment, notes)
                        showCreateDialog = false
                        showSuccessMessage = true // Trigger snackbar and navigation
                    },
                    rubikFontFamily = rubikFontFamily
                )
            }
        }
    }
}

@Composable
fun CreateMedicalRecordDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String) -> Unit,
    rubikFontFamily: FontFamily
) {
    var diagnosis by remember { mutableStateOf(TextFieldValue("")) }
    var treatment by remember { mutableStateOf(TextFieldValue("")) }
    var notes by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Add Medical Record",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = rubikFontFamily,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color(0xFF4CAF50)
                )

                OutlinedTextField(
                    value = diagnosis,
                    onValueChange = { diagnosis = it },
                    label = { Text("Diagnosis", fontFamily = rubikFontFamily) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4CAF50),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = treatment,
                    onValueChange = { treatment = it },
                    label = { Text("Treatment", fontFamily = rubikFontFamily) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4CAF50),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes", fontFamily = rubikFontFamily) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4CAF50),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", color = Color.White, fontFamily = rubikFontFamily)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSubmit(diagnosis.text, treatment.text, notes.text)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Submit", color = Color.White, fontFamily = rubikFontFamily)
                    }
                }
            }
        }
    }
}