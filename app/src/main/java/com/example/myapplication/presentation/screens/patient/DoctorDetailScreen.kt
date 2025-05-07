package com.example.myapplication.presentation.screens.patient

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun DoctorDetailScreen(
    navController: NavHostController,
    doctorName: String
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )

    val doctor = Doctor(doctorName, "Cardiologist", R.drawable.doctor)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
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
                text = "Doctor Details",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = doctor.imageRes),
                contentDescription = doctor.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = doctor.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily
            )
            Text(
                text = doctor.specialty,
                fontSize = 18.sp,
                color = Color.Gray,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Experience: 10 years\nLocation: City Hospital\nRating: 4.8/5",
                fontSize = 16.sp,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("appointment_booking") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FF8))
            ) {
                Text(
                    text = "Book Appointment",
                    color = Color.White,
                    fontFamily = rubikFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}