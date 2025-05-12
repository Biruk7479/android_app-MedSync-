package com.example.myapplication.data.model.patient

import com.example.myapplication.data.model.doctor.DoctorInfo
import com.example.myapplication.data.model.doctor.MedicationEntry
import com.example.myapplication.data.model.doctor.PatientInfo

data class PrescriptionResponse(
    val success: Boolean,
    val data: List<Prescription>?,
    val message: String? = null
)



data class MedicalRecordResponse(
    val success: Boolean,
    val data: List<MedicalRecord>?,
    val message: String? = null
)

data class MedicalRecord(
    val id: String?,
    val patientId: String?,
    val doctorId: String?,
    val diagnosis: String?,
    val treatment: String?,
    val notes: String?,
    val lastUpdated: String?,
    val doctorInfo: DoctorInfo?,
    val patientInfo: PatientInfo?
)
data class Prescription(
    val id: String?,
    val patientId: String?,
    val doctorId: String?, // Note: doctorInfo is not populated by backend
    val medicationEntries: List<MedicationEntry>?,
    val createdAt: String?
)