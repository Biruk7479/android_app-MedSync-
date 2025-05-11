package com.example.myapplication.presentation.screens.doctor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.doctor.AppointmentResponse
import com.example.myapplication.data.repository.doctor.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DoctorDashboardViewModel(
    private val repository: DoctorRepository
) : ViewModel() {

    sealed class DashboardState {
        object Loading : DashboardState()
        data class Success(val appointments: AppointmentResponse) : DashboardState()
        data class Error(val message: String) : DashboardState()
    }

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()

    private val _selectedDate = MutableStateFlow<Date>(Date())
    val selectedDate: StateFlow<Date> = _selectedDate.asStateFlow()

    init {
        fetchAppointments()
    }

    fun fetchAppointments(date: Date? = selectedDate.value) {
        viewModelScope.launch {
            try {
                _dashboardState.value = DashboardState.Loading
                val dateString = date?.let { repository.formatDateForApi(it) }
                repository.getDoctorAppointments(dateString, "scheduled").collect { response ->
                    Log.d("DoctorDashboardViewModel", "Fetched appointments: $response")
                    _dashboardState.value = DashboardState.Success(response)
                }
            } catch (e: Exception) {
                Log.e("DoctorDashboardViewModel", "Error fetching appointments: ${e.message}", e)
                _dashboardState.value = DashboardState.Error(
                    e.message ?: "Failed to load appointments"
                )
            }
        }
    }

    fun updateAppointmentStatus(appointmentId: String, status: String) {
        viewModelScope.launch {
            try {
                repository.updateAppointmentStatus(appointmentId, status).collect {
                    fetchAppointments() // Refresh appointments after status update
                }
            } catch (e: Exception) {
                Log.e("DoctorDashboardViewModel", "Error updating status: ${e.message}", e)
                _dashboardState.value = DashboardState.Error(
                    e.message ?: "Failed to update appointment status"
                )
            }
        }
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
        fetchAppointments(date)
    }

    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("EEE", Locale.getDefault())
        return sdf.format(Date()).uppercase()
    }

    fun formatDate(dateString: String?): String {
        if (dateString == null) return "N/A"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }
}

class DoctorDashboardViewModelFactory(
    private val repository: DoctorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorDashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorDashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}