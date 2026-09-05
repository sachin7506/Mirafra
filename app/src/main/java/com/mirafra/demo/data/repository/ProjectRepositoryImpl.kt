package com.mirafra.demo.data.repository

import com.mirafra.demo.data.remote.api.ApiService
import com.mirafra.demo.data.remote.model.request.CreateProjectRequest
import com.mirafra.demo.data.remote.model.response.CreateProjectResponse
import com.mirafra.demo.data.remote.model.response.Project
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProjectRepository {

    override suspend fun createProject(
        request: CreateProjectRequest
    ): Result<CreateProjectResponse> {
        return try {
            val response = apiService.createProject(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        return try {
            val response = apiService.getProjects()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.projects)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}