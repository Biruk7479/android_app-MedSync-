package com.example.myapplication.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Dashboard", "patient_dashboard", R.drawable.ic_home_unselected, R.drawable.ic_home_selected),
        BottomNavItem("Appointments", "appointment_booking", R.drawable.ic_appointments_unselected, R.drawable.ic_appointments_selected),
        BottomNavItem("Doctors", "doctors", R.drawable.ic_doctors_unselected, R.drawable.ic_doctors_selected),
        BottomNavItem("History", "medical_history", R.drawable.ic_medical_history_unselected, R.drawable.ic_medical_history_selected),
        BottomNavItem("Prescriptions", "prescriptions", R.drawable.ic_perscriptions_unselected, R.drawable.ic_perscriptions_selected),
    )

    NavigationBar(
        containerColor = Color(0xFF6B5FF8), // Purple background
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp) // Padding on all sides
            .fillMaxWidth()
            .height(50.dp) // Increased height for bigger icons
            .clip(RoundedCornerShape(32.dp)), // Rounded corners
        contentColor = Color.Transparent // Disable default content color
    ) {
        val currentRoute by navController.currentBackStackEntryAsState()
        val currentDestination = currentRoute?.destination?.route

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                        .clickable { // Add click handler for navigation
                            if (currentDestination != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(id = if (currentDestination == item.route) item.selectedIcon else item.unselectedIcon),
                        contentDescription = null, // Removed content description
                        modifier = Modifier
                            .size(30.dp) // Bigger icon size
                            .align(Alignment.Center), // Center the icon
                        tint = Color.Unspecified // Ensure no tint is applied
                    )
                }
            }
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val unselectedIcon: Int, val selectedIcon: Int)