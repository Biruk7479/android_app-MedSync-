//package com.example.myapplication.data.repository.doctor
//
//import com.example.myapplication.data.model.AuthPreferences
//import com.example.myapplication.data.model.doctor.Doctor
//import com.example.myapplication.data.remote.DoctorApi
//import retrofit2.HttpException
//import java.io.IOException
//
//class DoctorRepository(
//    private val authPreferences: AuthPreferences,
//    private val doctorApi: DoctorApi
//) {
//    suspend fun getDoctors(): List<Doctor> {
//        try {
//            val response = doctorApi.getDoctors()
//            if (response.success && response.data.isNotEmpty()) {
//                return response.data
//            } else {
//                throw Exception(response.message ?: "Failed to fetch doctors")
//            }
//        } catch (e: HttpException) {
//            throw Exception("HTTP error: ${e.message()}")
//        } catch (e: IOException) {
//            throw Exception("Network error: ${e.message}")
//        }
//    }
//}
package com.example.myapplication.data.repository.doctor

import com.example.myapplication.data.model.AuthPreferences
import com.example.myapplication.data.model.doctor.Doctor
import com.example.myapplication.data.remote.DoctorApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DoctorRepository(
    private val authPreferences: AuthPreferences,
    private val doctorApi: DoctorApi
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
    }

    private fun generateFallbackId(): String {
        return "fallback_${System.currentTimeMillis()}"
    }
}