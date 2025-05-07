package com.example.myapplication.data.repository.patient


import com.example.myapplication.R
import com.example.myapplication.data.model.patient.AppointmentModel
import com.example.myapplication.data.model.patient.Doctor
import kotlinx.coroutines.delay

class AppointmentRepository {
    suspend fun getAppointments(): List<AppointmentModel> {
        delay(1000) // Simulate network delay
        return listOf(
            AppointmentModel(
                id = "1",
                date = "11 June 2024",
                time = "08:00 - 12:00",
                doctor = Doctor("Dr. Strange Walker", "Internist Specialist Doctor", R.drawable.doctor)
            )
        )
    }
}