package com.example.myapplication.data.remote

import com.example.myapplication.data.model.doctor.AppointmentResponse
import com.example.myapplication.data.model.doctor.DoctorResponse
import com.example.myapplication.data.model.doctor.PatientDetailsResponse
import com.example.myapplication.data.model.doctor.UpdateStatusRequest
import com.example.myapplication.data.model.patient.PatientResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DoctorApi {
    @GET("api/admin/staff/doctor")
    suspend fun getDoctors(@Header("Authorization") authHeader: String): DoctorResponse

        @GET("/api/doctor/appointments")
        suspend fun getDoctorAppointments(
            @Header("Authorization") token: String,
            @Query("date") date: String? = null,
            @Query("status") status: String? = null
        ): AppointmentResponse
        @GET("/api/doctor/patients")
        suspend fun getDoctorPatients(
            @Header("Authorization") token: String
        ): PatientResponse

        @GET("/api/doctor/patients/{id}")
        suspend fun getPatientDetails(
            @Header("Authorization") token: String,
            @Path("id") patientId: String
        ): PatientDetailsResponse

//        @GET("/api/doctor/patients/{id}")
//        suspend fun getPatientDetails(
//            @Header("Authorization") token: String,
//            @Path("id") patientId: String
//        ): PatientDetailsResponse
//
//
//
//        @PUT("/api/doctor/appointments/{id}/status")
//        suspend fun updateAppointmentStatus(
//            @Header("Authorization") token: String,
//            @Path("id") appointmentId: String,
//            @Body request: UpdateStatusRequest
//        ): AppointmentResponse
    }

