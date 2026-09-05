package com.mirafra.demo.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirafra.demo.data.remote.model.request.SignInRequest
import com.mirafra.demo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) { email = value }

    fun onPasswordChange(value: String) { password = value }

    fun signIn() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            authRepository.signIn(
                SignInRequest(emailId = email, password = password)
            ).fold(
                onSuccess = { _uiState.value = LoginUiState.Success(it) },
                onFailure = { _uiState.value = LoginUiState.Error(it.message ?: "Something went wrong") }
            )
        }
    }

    fun resetState() { _uiState.value = LoginUiState.Idle }
}