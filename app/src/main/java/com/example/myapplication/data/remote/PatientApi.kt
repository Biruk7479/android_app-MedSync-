package com.example.myapplication.data.remote

import com.example.myapplication.data.model.patient.DashboardResponse
import retrofit2.http.*

interface PatientApi {
    @GET("/api/patient/dashboard")
    suspend fun getDashboard(@Header("Authorization") token: String): DashboardResponse

    @PUT("/api/patient/bookings/{id}/cancel")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Path("id") bookingId: String
    ): DashboardResponse
}