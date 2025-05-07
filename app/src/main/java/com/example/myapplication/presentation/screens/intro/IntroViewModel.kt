package com.example.myapplication.presentation.screens.intro

import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroViewModel : ViewModel() {

    private val _introScreenState = MutableStateFlow(
        IntroScreenState( // Default to first screen
            title = "Welcome to Medsync",
            description = "Your health companion made simple.",
            imageRes = R.drawable.intro1
        )
    )
    val introScreenState: StateFlow<IntroScreenState> = _introScreenState.asStateFlow()

    fun setScreen(screenNumber: Int) {
        when (screenNumber) {
            0 -> _introScreenState.value = IntroScreenState(
                title = "Welcome to Medsync",
                description = "Your health companion made simple.",
                imageRes = R.drawable.intro1
            )
            1 -> _introScreenState.value = IntroScreenState(
                title = "Book Appointments",
                description = "Easily schedule visits with your doctor.",
                imageRes = R.drawable.intro2
            )
            2 -> _introScreenState.value = IntroScreenState(
                title = "Track Prescriptions",
                description = "Keep up with your medications easily.",
                imageRes = R.drawable.intro3
            )
        }
    }
}
