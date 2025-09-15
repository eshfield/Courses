package com.example.courses.feature.signin.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel() : ViewModel() {
    private val _formState = MutableStateFlow(FormData())
    val formState = _formState.asStateFlow()

    private val invalidSymbols = Regex("[^A-Za-z0-9@+._%-]")

    fun updateEmail(newEmail: String) {
        if (invalidSymbols.containsMatchIn(newEmail)) return

        _formState.update { state ->
            state.copy(email = newEmail)
        }
        validateForm()
    }

    fun updatePassword(newPassword: String) {
        _formState.update { state ->
            state.copy(password = newPassword)
        }
        validateForm()
    }

    private fun validateForm() {
        val (email, password) = _formState.value
        val invalidClauses = listOf(
            email.isBlank(),
            !Patterns.EMAIL_ADDRESS.matcher(email).matches(),
            password.isBlank(),
            password.length < 6,
        )

        _formState.update { state ->
            state.copy(isFormValid = !invalidClauses.any { it })
        }
    }
}

data class FormData(
    val email: String = "",
    val password: String = "",
    val isFormValid: Boolean = false,
)
