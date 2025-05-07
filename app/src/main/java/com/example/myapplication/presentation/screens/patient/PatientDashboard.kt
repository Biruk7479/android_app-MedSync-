package com.example.myapplication.presentation.screens.patient

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.doctor.Doctor
import com.example.myapplication.data.model.patient.AppointmentModel
import com.example.myapplication.data.repository.patient.AppointmentRepository
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun PatientDashboardScreen(navController: NavHostController) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val viewModel: AppointmentViewModel = viewModel { AppointmentViewModel(AppointmentRepository()) }
    var showSettingsPopup by remember { mutableStateOf(false) }
    var showNotificationPage by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Top Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.doctor), // Replace with actual profile image
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
                            text = "Abebe Bekele",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFontFamily
                        )
                    }
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showNotificationPage = true },
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

            // Doctor Consult Card
            DoctorConsultCard(rubikFontFamily)

            // Top Doctors Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top Doctors",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = rubikFontFamily
                )
                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    color = Color(0xFF6B5FF8),
                    modifier = Modifier
                        .clickable { /* Navigate to all doctors */ }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Doctor(
                        id = "doc2",
                        name = "Dr. Marcus Horiz",
                        specialty = "Cardiologist",
                        imageRes = R.drawable.doctor,
                        rating = 4.7f,
                        hospital = null,
                        experienceYears = null
                    ),
                    Doctor(
                        id = "doc3",
                        name = "Dr. Maria Elena",
                        specialty = "Psychologist",
                        imageRes = R.drawable.doctor,
                        rating = 4.9f,
                        hospital = null,
                        experienceYears = null
                    ),
                    Doctor(
                        id = "doc4",
                        name = "Dr. Stevi Jes",
                        specialty = "Orthopedist",
                        imageRes = R.drawable.doctor,
                        rating = 4.8f,
                        hospital = null,
                        experienceYears = null
                    )
                ).forEach { doctor ->
                    TopDoctorCard(doctor = doctor, rubikFontFamily)
                }
            }

            // Upcoming Appointments
            val appointments by viewModel.appointments.collectAsState()
            appointments.take(1).forEach { appointment -> // Only show one card
                AppointmentCard(appointment = appointment, rubikFontFamily)
                Spacer(modifier = Modifier.height(8.dp))
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
                        text = "Edit Profile",
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { /* Navigate to edit profile */ }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { navController.popBackStack() }
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

    // Notification Subpage
    if (showNotificationPage) {
        NotificationScreen(navController) { showNotificationPage = false }
    }
}

@Composable
fun DoctorConsultCard(rubikFontFamily: FontFamily) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF7563F7), Color(0xFF1D7885))
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Trusted doctor on your schedule",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Consult A Doctor\n— Book Today!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rubikFontFamily
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            val avatarIds = listOf(R.drawable.profiles) // Changed to single photo
                            avatarIds.forEachIndexed { index, id ->
                                Image(
                                    painter = painterResource(id = id),
                                    contentDescription = "Patient Avatar",
                                    modifier = Modifier
                                        .size(95.dp)
//                                        .offset(x = (-10 * index).dp)

                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "30,000+\nHappy Patients",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = rubikFontFamily
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.doctor),
                    contentDescription = "Doctor",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(0.9f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun TopDoctorCard(doctor: Doctor, rubikFontFamily: FontFamily) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .border(0.8.dp, Color(0xFFD3D3D3), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.3.dp) // Reduced shadow
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = doctor.imageRes),
                contentDescription = doctor.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .padding(top = 20.dp), // Increased top padding
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = doctor.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = rubikFontFamily
                )
                Text(
                    text = doctor.specialty,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
                doctor.rating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFF1E6FF), RoundedCornerShape(4.dp)) // Light purple box covering both star and rating
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Rating",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF6B5FF8)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$rating",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = rubikFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: AppointmentModel, rubikFontFamily: FontFamily) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6B5FF8)), // Purple background for the entire card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Internal padding within the purple background
        ) {
            Text(
                text = "Upcoming Appointments",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Date Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(Color(0xFF8A7DFF), RoundedCornerShape(8.dp)) // Lighter purple box
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_blank_outline),
                        contentDescription = "Date",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = appointment.date,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                    Text(
                        text = "Appointment Date",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = rubikFontFamily
                    )
                }
                // Time Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(Color(0xFF8A7DFF), RoundedCornerShape(8.dp)) // Lighter purple box
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock_check_outline),
                        contentDescription = "Time",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = appointment.time,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = rubikFontFamily
                    )
                    Text(
                        text = "Appointment Time",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = rubikFontFamily
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Doctor Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)) // White box for doctor info
                    .padding(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = appointment.doctor.imageRes),
                        contentDescription = appointment.doctor.name,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = appointment.doctor.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = rubikFontFamily,
                            color = Color.Black
                        )
                        Text(
                            text = appointment.doctor.specialty,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = rubikFontFamily
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Vector Image",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { /* Navigate to chat */ },
                        tint = Color(0xFF6B5FF8)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationScreen(navController: NavHostController, onDismiss: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("No new notifications.", fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Close")
            }
        }
    }
}