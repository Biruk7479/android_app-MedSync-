package com.example.myapplication.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import java.net.URLEncoder

data class DoctorBottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
fun DoctorBottomNavBar(
    navController: NavHostController,
    authPreferences: AuthPreferences,
    currentRoute: String
) {
    val items = listOf(
        DoctorBottomNavItem("doctor_dashboard", R.drawable.ic_home_unselected, "Home"),
        DoctorBottomNavItem("doctor_chat", R.drawable.ic_chat, "Chat"),
        DoctorBottomNavItem("profile", R.drawable.ic_users_unselected, "Profile"),
        DoctorBottomNavItem("calendar", R.drawable.ic_appointments_unselected, "Calendar")
    )

    NavigationBar(
        containerColor = Color(0xFFD8C4E7),
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color(0xFF6B5FF8) else Color.Black
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (currentRoute == item.route) Color(0xFF6B5FF8) else Color.Black
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        val name = authPreferences.getName() ?: "Doctor"
                        val encodedName = URLEncoder.encode(name, "UTF-8")
                        val destination = if (item.route == "doctor_dashboard") {
                            "doctor_dashboard/$encodedName"
                        } else {
                            item.route
                        }
                        navController.navigate(destination) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}