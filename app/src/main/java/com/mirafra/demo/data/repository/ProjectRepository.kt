package com.mirafra.demo.data.repository

import com.mirafra.demo.data.remote.model.request.CreateProjectRequest
import com.mirafra.demo.data.remote.model.response.CreateProjectResponse
import com.mirafra.demo.data.remote.model.response.Project

interface ProjectRepository {
    suspend fun createProject(request: CreateProjectRequest): Result<CreateProjectResponse>

    suspend fun getProjects(): Result<List<Project>>
}