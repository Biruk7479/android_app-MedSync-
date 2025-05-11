//package com.example.myapplication.data.repository.doctor
//
//import com.example.myapplication.data.model.AuthPreferences
//import com.example.myapplication.data.model.doctor.AppointmentResponse
//import com.example.myapplication.data.model.doctor.Doctor
//import com.example.myapplication.data.model.doctor.PatientDetailsResponse
//import com.example.myapplication.data.model.doctor.UpdateStatusRequest
//import com.example.myapplication.data.remote.DoctorApi
//import com.example.myapplication.data.remote.NetworkProvider
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import retrofit2.HttpException
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*
//import android.util.Log
//
//class DoctorRepository(
//    private val authPreferences: AuthPreferences,
//    private val doctorApi: DoctorApi = NetworkProvider.doctorApi
//) {
//    fun getDoctors(): Flow<List<Doctor>> = flow {
//        try {
//            val token = authPreferences.getToken() ?: throw IllegalStateException("No token found")
//            val response = doctorApi.getDoctors("Bearer $token")
//            if (response.success) {
//                emit(response.data.map { it.copy(id = it.id ?: generateFallbackId()) })
//            } else {
//                throw Exception("API error: ${response.message}")
//            }
//        } catch (e: HttpException) {
//            throw Exception("Network error: ${e.message()}")
//        } catch (e: IOException) {
//            throw Exception("IO error: ${e.message}")
//        }
//    }.flowOn(Dispatchers.IO)
//
//    fun getDoctorAppointments(date: String? = null, status: String? = null): Flow<AppointmentResponse> = flow {
//        val token = authPreferences.getToken() ?: throw Exception("No token")
//        try {
//            Log.d("DoctorRepository", "Fetching appointments with token: Bearer $token, date: $date, status: $status")
//            val response = doctorApi.getDoctorAppointments("Bearer $token", status, date)
//            Log.d("DoctorRepository", "Appointments response: $response")
//            if (response.success) {
//                emit(response)
//            } else {
//                throw Exception(response.message ?: "Failed to fetch appointments")
//            }
//        } catch (e: Exception) {
//            Log.e("DoctorRepository", "Error fetching appointments: ${e.message}", e)
//            throw Exception("Failed to fetch appointments: ${e.message}")
//        }
//    }.flowOn(Dispatchers.IO)
//
//    fun getPatientDetails(patientId: String): Flow<PatientDetailsResponse> = flow {
//        val token = authPreferences.getToken() ?: throw Exception("No token")
//        try {
//            Log.d("DoctorRepository", "Fetching patient details for $patientId with token: Bearer $token")
//            val response = doctorApi.getPatientDetails("Bearer $token", patientId)
//            Log.d("DoctorRepository", "Patient details response: $response")
//            if (response.success) {
//                emit(response)
//            } else {
//                throw Exception(response.message ?: "Failed to fetch patient details")
//            }
//        } catch (e: Exception) {
//            Log.e("DoctorRepository", "Error fetching patient details: ${e.message}", e)
//            throw Exception("Failed to fetch patient details: ${e.message}")
//        }
//    }.flowOn(Dispatchers.IO)
//
//    fun updateAppointmentStatus(appointmentId: String, status: String): Flow<AppointmentResponse> = flow {
//        val token = authPreferences.getToken() ?: throw Exception("No token")
//        try {
//            Log.d("DoctorRepository", "Updating appointment $appointmentId to status $status with token: Bearer $token")
//            val request = UpdateStatusRequest(status)
//            val response = doctorApi.updateAppointmentStatus("Bearer $token", appointmentId, request)
//            Log.d("DoctorRepository", "Update status response: $response")
//            if (response.success) {
//                emit(response)
//            } else {
//                throw Exception(response.message ?: "Failed to update appointment status")
//            }
//        } catch (e: Exception) {
//            Log.e("DoctorRepository", "Error updating appointment status: ${e.message}", e)
//            throw Exception("Failed to update appointment status: ${e.message}")
//        }
//    }.flowOn(Dispatchers.IO)
//
//    fun formatDateForApi(date: Date): String {
//        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        return sdf.format(date)
//    }
//
//    private fun generateFallbackId(): String {
//        return "fallback_${System.currentTimeMillis()}"
//    }
//}

package com.example.myapplication.data.repository.doctor

import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.model.doctor.AppointmentResponse
import com.example.myapplication.data.model.doctor.Doctor
import com.example.myapplication.data.model.doctor.PatientDetailsResponse
import com.example.myapplication.data.model.doctor.UpdateStatusRequest
import com.example.myapplication.data.remote.DoctorApi
import com.example.myapplication.data.remote.NetworkProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log

class DoctorRepository(
    private val authPreferences: AuthPreferences,
    private val doctorApi: DoctorApi = NetworkProvider.doctorApi
) {
    fun getDoctors(): Flow<List<Doctor>> = flow {
        try {
            val token = authPreferences.getToken() ?: throw IllegalStateException("No token found")
            val response = doctorApi.getDoctors("Bearer $token")
            if (response.success) {
                emit(response.data.map { it.copy(id = it.id ?: generateFallbackId()) })
            } else {
                throw Exception("API error: ${response.message}")
            }
        } catch (e: HttpException) {
            throw Exception("Network error: ${e.message()}")
        } catch (e: IOException) {
            throw Exception("IO error: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDoctorAppointments(date: String? = null, status: String? = null): Flow<AppointmentResponse> = flow {
        val token = authPreferences.getToken() ?: throw Exception("No token")
        try {
            Log.d("DoctorRepository", "Fetching appointments with token: Bearer $token, date: $date, status: $status")
            val response = doctorApi.getDoctorAppointments("Bearer $token", status, date)
            Log.d("DoctorRepository", "Appointments response: $response")
            if (response.success) {
                emit(response)
            } else {
                throw Exception(response.message ?: "Failed to fetch appointments")
            }
        } catch (e: Exception) {
            Log.e("DoctorRepository", "Error fetching appointments: ${e.message}", e)
            throw Exception("Failed to fetch appointments: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getPatientDetails(patientId: String): Flow<PatientDetailsResponse> = flow {
        val token = authPreferences.getToken() ?: throw Exception("No token")
        try {
            Log.d("DoctorRepository", "Fetching patient details for $patientId with token: Bearer $token")
            val response = doctorApi.getPatientDetails("Bearer $token", patientId)
            Log.d("DoctorRepository", "Patient details response: $response")
            response.data?.prescriptions?.forEach { prescription ->
                Log.d("DoctorRepository", "Prescription: id=${prescription.id}, medication=${prescription.medication}, dosage=${prescription.dosage}, date=${prescription.date}")
            }
            if (response.success) {
                emit(response)
            } else {
                throw Exception(response.message ?: "Failed to fetch patient details")
            }
        } catch (e: Exception) {
            Log.e("DoctorRepository", "Error fetching patient details: ${e.message}", e)
            throw Exception("Failed to fetch patient details: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun updateAppointmentStatus(appointmentId: String, status: String): Flow<AppointmentResponse> = flow {
        val token = authPreferences.getToken() ?: throw Exception("No token")
        try {
            Log.d("DoctorRepository", "Updating appointment $appointmentId to status $status with token: Bearer $token")
            val request = UpdateStatusRequest(status)
            val response = doctorApi.updateAppointmentStatus("Bearer $token", appointmentId, request)
            Log.d("DoctorRepository", "Update status response: $response")
            if (response.success) {
                emit(response)
            } else {
                throw Exception(response.message ?: "Failed to update appointment status")
            }
        } catch (e: Exception) {
            Log.e("DoctorRepository", "Error updating appointment status: ${e.message}", e)
            throw Exception("Failed to update appointment status: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun formatDateForApi(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }

    private fun generateFallbackId(): String {
        return "fallback_${System.currentTimeMillis()}"
    }
}