package com.mirafra.demo.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirafra.demo.data.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProjectsUiState>(ProjectsUiState.Idle)
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    init {
        fetchProjects()   // auto-load on screen open
    }

    fun fetchProjects() {
        viewModelScope.launch {
            _uiState.value = ProjectsUiState.Loading
            projectRepository.getProjects().fold(
                onSuccess = { _uiState.value = ProjectsUiState.Success(it) },
                onFailure = { _uiState.value = ProjectsUiState.Error(it.message ?: "Something went wrong") }
            )
        }
    }
}