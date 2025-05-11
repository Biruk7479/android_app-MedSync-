package com.example.myapplication.presentation.screens.doctor


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.doctor.PatientDetailsResponse
import com.example.myapplication.data.repository.doctor.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PatientDetailsViewModel(
    private val repository: DoctorRepository
) : ViewModel() {

    sealed class PatientState {
        object Loading : PatientState()
        data class Success(val patientDetails: PatientDetailsResponse) : PatientState()
        data class Error(val message: String) : PatientState()
    }

    private val _patientState = MutableStateFlow<PatientState>(PatientState.Loading)
    val patientState: StateFlow<PatientState> = _patientState.asStateFlow()

    fun fetchPatientDetails(patientId: String) {
        viewModelScope.launch {
            try {
                _patientState.value = PatientState.Loading
                repository.getPatientDetails(patientId).collect { response ->
                    Log.d("PatientDetailsViewModel", "Fetched patient details: $response")
                    _patientState.value = PatientState.Success(response)
                }
            } catch (e: Exception) {
                Log.e("PatientDetailsViewModel", "Error fetching patient details: ${e.message}", e)
                _patientState.value = PatientState.Error(
                    e.message ?: "Failed to load patient details"
                )
            }
        }
    }
}

class PatientDetailsViewModelFactory(
    private val repository: DoctorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}