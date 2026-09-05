package com.mirafra.demo.ui.projects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirafra.demo.data.remote.model.request.CreateProjectRequest
import com.mirafra.demo.data.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    var projectName   by mutableStateOf("")
        private set

    var objective     by mutableStateOf("")
        private set

    var analysisFocus by mutableStateOf("")
        private set

    val isFormValid: Boolean
        get() = projectName.isNotBlank() && objective.isNotBlank()

    private val _uiState = MutableStateFlow<CreateProjectUiState>(CreateProjectUiState.Idle)
    val uiState: StateFlow<CreateProjectUiState> = _uiState.asStateFlow()

    fun onProjectNameChange(value: String)   { projectName   = value }
    fun onObjectiveChange(value: String)     { objective     = value }
    fun onAnalysisFocusChange(value: String) { analysisFocus = value }

    fun createProject() {
        viewModelScope.launch {
            _uiState.value = CreateProjectUiState.Loading
            projectRepository.createProject(
                CreateProjectRequest(
                    name          = projectName,
                    objective     = objective,
                    analysisFocus = analysisFocus.ifBlank { null }
                )
            ).fold(
                onSuccess = { _uiState.value = CreateProjectUiState.Success(it) },
                onFailure = { _uiState.value = CreateProjectUiState.Error(it.message ?: "Something went wrong") }
            )
        }
    }

    fun resetState() { _uiState.value = CreateProjectUiState.Idle }
}