package com.mirafra.demo.ui.auth.login


import com.mirafra.demo.data.remote.model.response.SignInResponse

interface LoginUiState {
    object Idle    : LoginUiState
    object Loading : LoginUiState
    data class Success(val response: SignInResponse) : LoginUiState
    data class Error(val message: String)            : LoginUiState
}