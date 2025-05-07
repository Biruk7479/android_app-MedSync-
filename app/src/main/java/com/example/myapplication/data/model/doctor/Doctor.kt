package com.example.myapplication.data.model.doctor


data class Doctor(
    val id: String, // Unique identifier for the doctor
    val name: String,
    val specialty: String,
    val imageRes: Int, // Resource ID for the doctor's image
    val rating: Float?, // Rating for "Top Doctors" card (nullable for cases where rating isn't available)
    val hospital: String? = null, // Optional: Hospital affiliation for DoctorDetailScreen
    val experienceYears: Int? = null // Optional: Years of experience for DoctorDetailScreen
)