package com.mirafra.demo.ui.projects

import com.mirafra.demo.data.remote.model.response.Project

interface ProjectsUiState {
    object Idle                                      : ProjectsUiState
    object Loading                                   : ProjectsUiState
    data class Success(val projects: List<Project>)  : ProjectsUiState
    data class Error(val message: String)            : ProjectsUiState
}