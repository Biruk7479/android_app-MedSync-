package com.example.myapplication.data.remote

import com.example.myapplication.data.model.doctor.DoctorResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface DoctorApi {
    @GET("api/admin/staff/doctor")
    suspend fun getDoctors(@Header("Authorization") authHeader: String): DoctorResponse
}