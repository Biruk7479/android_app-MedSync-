package com.example.myapplication.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.model.LoginResponse
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val rubikFontFamily = FontFamily(
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_bold, FontWeight.Bold)
    )
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(AuthRepository())
    )
    val loginState: LoginState by viewModel.loginState.collectAsState()

    // Handle login state with navigation and feedback
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val response = (loginState as LoginState.Success).response
                val destination = when (response.role.lowercase()) {
                    "patient" -> "patient_dashboard/${response.name}"
                    "doctor" -> "doctor_dashboard/${response.name}"
                    "triage" -> "triage_dashboard/${response.name}"
                    "admin" -> "admin_dashboard/${response.name}"
                    else -> {
                        Log.e("LoginScreen", "Unknown role: ${response.role}")
                        "patient_dashboard/${response.name}" // Default fallback
                    }
                }
                navController.navigate(destination) {
                    popUpTo("login") { inclusive = true }
                }
                viewModel.resetState()
                Toast.makeText(context, "Login successful: ${response.message}", Toast.LENGTH_SHORT).show()
            }
            is LoginState.Error -> {
                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Welcome to Medsync!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = rubikFontFamily,
                color = Color(0xFF6B5FF8)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Stay connected to your health journey.",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = rubikFontFamily
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text("Email") },
                placeholder = { Text("you@example.com") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Password") },
                placeholder = { Text("Your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Your Password?",
                color = Color(0xFF6B5FF8),
                fontFamily = rubikFontFamily,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { /* TODO: Navigate to forgot password screen */ },
                style = MaterialTheme.typography.labelSmall
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    Log.d("LoginScreen", "Login button clicked")
                    viewModel.login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FF8)),
                enabled = loginState !is LoginState.Loading
            ) {
                Text(
                    if (loginState is LoginState.Loading) "Logging in..." else "Login",
                    color = Color.White,
                    fontFamily = rubikFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "New to Medsync? Register",
                color = Color(0xFF6B5FF8),
                fontFamily = rubikFontFamily,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// ViewModel Factory for manual instantiation
class LoginViewModelFactory(
    private val repository: AuthRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}