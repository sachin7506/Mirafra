package com.mirafra.demo.ui.projects

import com.mirafra.demo.data.remote.model.response.CreateProjectResponse

interface CreateProjectUiState {
    object Idle      : CreateProjectUiState
    object Loading   : CreateProjectUiState
    data class Success(val response: CreateProjectResponse) : CreateProjectUiState
    data class Error(val message: String)                   : CreateProjectUiState
}