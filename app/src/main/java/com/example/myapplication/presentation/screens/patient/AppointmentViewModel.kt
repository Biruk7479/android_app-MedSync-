package com.example.myapplication.presentation.screens.patient


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.patient.AppointmentModel
import com.example.myapplication.data.repository.patient.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {
    private val _appointments = MutableStateFlow<List<AppointmentModel>>(emptyList())
    val appointments: StateFlow<List<AppointmentModel>> = _appointments

    init {
        fetchAppointments()
    }

    private fun fetchAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAppointments()
        }
    }
}