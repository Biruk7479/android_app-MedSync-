package com.example.myapplication.presentation.screens.doctor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.doctor.CreateMedicalRecordRequest
import com.example.myapplication.data.model.doctor.MedicalRecord
import com.example.myapplication.data.model.doctor.Patient
import com.example.myapplication.data.model.doctor.PatientDetailsResponse
import com.example.myapplication.data.repository.doctor.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PatientDetailsViewModel(
    private val repository: DoctorRepository,
    private val patientId: String
) : ViewModel() {

    sealed class PatientDetailsState {
        object Loading : PatientDetailsState()
        data class Success(val patient: Patient, val medicalRecords: List<MedicalRecord>) : PatientDetailsState()
        data class Error(val message: String) : PatientDetailsState()
    }

    private val _patientDetailsState = MutableStateFlow<PatientDetailsState>(PatientDetailsState.Loading)
    val patientDetailsState: StateFlow<PatientDetailsState> = _patientDetailsState.asStateFlow()

    init {
        fetchPatientDetails()
    }

    fun fetchPatientDetails() {
        viewModelScope.launch {
            try {
                _patientDetailsState.value = PatientDetailsState.Loading
                val patientResponse = repository.getPatientDetails(patientId).first()
                Log.d("PatientDetailsViewModel", "Patient response: $patientResponse")
                if (!patientResponse.success || patientResponse.data == null) {
                    throw Exception(patientResponse.message ?: "Failed to fetch patient details")
                }
                val patient = patientResponse.data.patient
                val medicalRecordsResponse = repository.getPatientMedicalRecords(patientId).first()
                Log.d("PatientDetailsViewModel", "Medical records response: $medicalRecordsResponse")
                val medicalRecords = if (medicalRecordsResponse.success) {
                    medicalRecordsResponse.data
                } else {
                    throw Exception(medicalRecordsResponse.message ?: "Failed to fetch medical records")
                }
                _patientDetailsState.value = PatientDetailsState.Success(patient, medicalRecords)
            } catch (e: Exception) {
                Log.e("PatientDetailsViewModel", "Error: ${e.message}", e)
                _patientDetailsState.value = PatientDetailsState.Error(
                    e.message ?: "Failed to load patient details"
                )
            }
        }
    }

    fun createMedicalRecord(diagnosis: String, treatment: String, notes: String) {
        viewModelScope.launch {
            try {
                val request = CreateMedicalRecordRequest(
                    patientId = patientId,
                    diagnosis = diagnosis,
                    treatment = treatment,
                    notes = notes
                )
                val response = repository.createMedicalRecord(request).first()
                Log.d("PatientDetailsViewModel", "Create medical record response: $response")
                if (!response.success) {
                    throw Exception(response.message ?: "Failed to create medical record")
                }
                fetchPatientDetails() // Refresh after creation
            } catch (e: Exception) {
                Log.e("PatientDetailsViewModel", "Error creating medical record: ${e.message}", e)
                _patientDetailsState.value = PatientDetailsState.Error(
                    e.message ?: "Failed to create medical record"
                )
            }
        }
    }
}

class PatientDetailsViewModelFactory(
    private val repository: DoctorRepository,
    private val patientId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientDetailsViewModel(repository, patientId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}