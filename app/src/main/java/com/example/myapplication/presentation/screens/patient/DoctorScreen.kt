package com.example.myapplication.presentation.screens.patient


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun DoctorsScreen(
    navController: NavHostController,
    authPreferences: AuthPreferences
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )

    val currentDoctor = Doctor("Dr. John Smith", "Cardiologist", R.drawable.doctor)
    val recentDoctors = listOf(
        Doctor("Dr. Sarah Johnson", "Neurologist", R.drawable.doctor),
        Doctor("Dr. Michael Brown", "Pediatrician", R.drawable.doctor)
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController , authPreferences ) }
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
                text = "Your Doctors",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Current Doctor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            DoctorCard(doctor = currentDoctor) {
                navController.navigate("doctor_detail/${currentDoctor.name}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recent Doctors",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            recentDoctors.forEach { doctor ->
                DoctorCard(doctor = doctor) {
                    navController.navigate("doctor_detail/${doctor.name}")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = doctor.imageRes),
                contentDescription = doctor.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = doctor.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = rubikFontFamily
                )
                Text(
                    text = doctor.specialty,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
            }
        }
    }
}

data class Doctor(val name: String, val specialty: String, val imageRes: Int)