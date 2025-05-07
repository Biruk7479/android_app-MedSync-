package com.example.myapplication.presentation.screens.triage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences

@Composable
fun TriageDashboardScreen(
    navController: NavHostController,
    userName: String,
    authPreferences: AuthPreferences
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    var showSettingsPopup by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome, Triage Nurse",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = rubikFontFamily
                    )
                    Text(
                        text = userName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = rubikFontFamily
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.cog_outline),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { showSettingsPopup = true },
                    tint = Color(0xFF6B5FF8)
                )
            }

            // Placeholder Content
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Triage Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Review patient cases and assign priorities.",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = rubikFontFamily,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("triage_cases") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FF8))
            ) {
                Text("View Triage Cases", color = Color.White, fontFamily = rubikFontFamily)
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
                        modifier = Modifier.clickable { navController.navigate("edit_profile") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            authPreferences.clearAuthData()
                            navController.navigate("login") {
                                popUpTo("triage_dashboard") { inclusive = true }
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