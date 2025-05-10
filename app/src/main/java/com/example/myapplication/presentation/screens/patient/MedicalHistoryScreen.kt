package com.example.myapplication.presentation.screens.patient

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
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.navigation.BottomNavigationBar

@Composable
fun MedicalHistoryScreen(navController: NavHostController, authPreferences: AuthPreferences) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )

    // Tab state
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("All Records", "My Medical Records")

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

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color(0xFF6B5FF8)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = rubikFontFamily
                            )
                        },
                        selectedContentColor = Color(0xFF6B5FF8),
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Content
            when (selectedTabIndex) {
                0 -> AllRecordsContent(rubikFontFamily)
                1 -> MyMedicalRecordsContent(rubikFontFamily)
            }
        }
    }
}

@Composable
fun AllRecordsContent(rubikFontFamily: FontFamily) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "All Records",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = rubikFontFamily
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "List of all medical records will be here.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
            }
        }
    }
}

@Composable
fun MyMedicalRecordsContent(rubikFontFamily: FontFamily) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "My Medical Records",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = rubikFontFamily
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Your personal medical records will be here.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = rubikFontFamily
                )
            }
        }
    }
}