//package com.example.myapplication.presentation.screens.admin
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.example.myapplication.R
//
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun AdminDashboardScreen(navController: NavHostController) {
//    val rubikFontFamily = FontFamily(
//        Font(R.font.rubik_regular, FontWeight.Normal),
//        Font(R.font.rubik_medium, FontWeight.Medium),
//        Font(R.font.rubik_bold, FontWeight.Bold)
//    )
//
//    Scaffold(
//        modifier = Modifier.background(Color.White),
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Users",
//                        fontFamily = rubikFontFamily,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        color = Color(0xFF6B5FF8)
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color(0xFF6B5FF8)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(innerPadding)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Users Management",
//                fontFamily = rubikFontFamily,
//                fontSize = 16.sp,
//                color = Color.Gray
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = { navController.popBackStack() },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FF8))
//            ) {
//                Text(
//                    text = "Back",
//                    color = Color.White,
//                    fontFamily = rubikFontFamily
//                )
//            }
//        }
//    }
//}
//

package com.example.myapplication.presentation.screens.admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.model.doctor.Doctor
import com.example.myapplication.data.remote.NetworkProvider
import com.example.myapplication.data.repository.doctor.DoctorRepository
import com.example.myapplication.presentation.screens.admin.AdminBottomBar

@Composable
fun AdminDashboardScreen(
    navController: NavHostController,
    name: String,
    authPreferences: AuthPreferences
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val context = LocalContext.current
    val viewModel: AdminUserViewModel = viewModel {
        AdminUserViewModel(DoctorRepository(authPreferences, NetworkProvider.doctorApi))
    }
    var selectedTab by remember { mutableStateOf("Doctors") }
    var showSettingsPopup by remember { mutableStateOf(false) }
    var showNotificationPage by remember { mutableStateOf(false) }
    val tabs = listOf("Doctors", "Triages", "Admins", "Patients")
    val doctorState by viewModel.doctorState.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        bottomBar = { AdminBottomBar(navController, name) }
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
                            text = name,
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
        }
    }
}
