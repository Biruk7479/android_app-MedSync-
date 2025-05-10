package com.example.myapplication.data.model.patient

import com.google.gson.annotations.SerializedName

data class Patient(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)