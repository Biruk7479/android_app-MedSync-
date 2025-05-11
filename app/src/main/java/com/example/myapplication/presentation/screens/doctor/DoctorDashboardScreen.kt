package com.example.myapplication.presentation.screens.doctor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.model.doctor.Appointment
import com.example.myapplication.data.remote.NetworkProvider
import com.example.myapplication.data.repository.doctor.DoctorRepository
import com.example.myapplication.navigation.DoctorBottomNavBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DoctorDashboardScreen(
    navController: NavHostController,
    authPreferences: AuthPreferences
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val doctorApi = NetworkProvider.doctorApi
    val viewModel: DoctorDashboardViewModel = viewModel(
        factory = DoctorDashboardViewModelFactory(DoctorRepository(authPreferences, doctorApi))
    )
    val dashboardState by viewModel.dashboardState.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    var showSettingsPopup by remember { mutableStateOf(false) }
    val doctorName by remember { mutableStateOf(authPreferences.getName() ?: "Doctor") }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD8C4E7))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.doctor),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Hi, Welcome Back",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = rubikFontFamily
                        )
                        Text(
                            text = doctorName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFontFamily,
                            color = Color.Black
                        )
                    }
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.navigate("notifications") },
                        tint = Color(0xFF6B5FF8)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.cog_outline),
                        contentDescription = "Settings",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showSettingsPopup = true },
                        tint = Color(0xFF6B5FF8)
                    )
                }
            }
        },
        bottomBar = {
            DoctorBottomNavBar(
                navController = navController,
                authPreferences = authPreferences,
                currentRoute = "doctor_dashboard"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Patients", fontFamily = rubikFontFamily) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Search",
                        tint = Color(0xFF6B5FF8)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6B5FF8),
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Calendar Section (Light Purple)
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD8C4E7))
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD8C4E7))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                    val currentDay = viewModel.getCurrentDay()
                    days.forEachIndexed { index, day ->
                        val calendar = Calendar.getInstance()
                        calendar.time = Date()
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3 + index)
                        val isSelected = calendar.time == selectedDate
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(
                                    if (isSelected) Color(0xFF6B5FF8) else Color(0xFFD8C4E7),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.setSelectedDate(calendar.time)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = day,
                                    fontSize = 12.sp,
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontFamily = rubikFontFamily
                                )
                                Text(
                                    text = calendar.get(Calendar.DAY_OF_MONTH).toString(),
                                    fontSize = 16.sp,
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontFamily = rubikFontFamily
                                )
                            }
                        }
                    }
                }
                Text(
                    text = "${SimpleDateFormat("dd", Locale.getDefault()).format(selectedDate)} ${
                        SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate)
                    }${if (isToday(selectedDate)) " - Today" else ""}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                // Appointments Card (White inside Purple)
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    when (val state = dashboardState) {
                        is DoctorDashboardViewModel.DashboardState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                        is DoctorDashboardViewModel.DashboardState.Success -> {
                            val appointments = state.appointments.data.filter {
                                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate) ==
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.date)
                                                ?: Date()
                                        )
                            }
                            if (appointments.isEmpty()) {
                                Text(
                                    text = "No appointments for this date",
                                    fontFamily = rubikFontFamily,
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(16.dp)
                                )
                            } else {
                                LazyColumn {
                                    items(appointments) { appointment ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = appointment.time,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = rubikFontFamily,
                                                color = Color(0xFF6B5FF8)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = appointment.patient.name,
                                                fontSize = 14.sp,
                                                fontFamily = rubikFontFamily,
                                                color = Color.Black
                                            )
                                        }
                                        Divider()
                                    }
                                }
                            }
                        }
                        is DoctorDashboardViewModel.DashboardState.Error -> {
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

            Spacer(modifier = Modifier.height(16.dp))

            // Patients List
            Text(
                text = "Patients",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn {
                    items(listOf("Patient 1", "Patient 2", "Patient 3")) { patient ->
                        Text(
                            text = patient,
                            fontSize = 16.sp,
                            fontFamily = rubikFontFamily,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { /* Handle patient click */ }
                        )
                        Divider()
                    }
                }
            }
        }
    }

    // Settings Popup
    if (showSettingsPopup) {
        Dialog(onDismissRequest = { showSettingsPopup = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rubikFontFamily
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            authPreferences.clearAuthData()
                            navController.navigate("login") {
                                popUpTo("doctor_dashboard") { inclusive = true }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showSettingsPopup = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FF8))
                    ) {
                        Text("Close", color = Color.White, fontFamily = rubikFontFamily)
                    }
                }
            }
        }
    }
}

fun isToday(date: Date): Boolean {
    val today = Calendar.getInstance()
    val selected = Calendar.getInstance()
    selected.time = date
    return today.get(Calendar.YEAR) == selected.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == selected.get(Calendar.DAY_OF_YEAR)
}

fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}