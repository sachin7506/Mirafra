package com.mirafra.demo.data.remote.api

import com.mirafra.demo.data.remote.model.request.CreateProjectRequest
import com.mirafra.demo.data.remote.model.request.SignInRequest
import com.mirafra.demo.data.remote.model.response.CreateProjectResponse
import com.mirafra.demo.data.remote.model.response.ProjectsResponse
import com.mirafra.demo.data.remote.model.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST("api/v1/auth/signin")
    suspend fun signIn(
        @Body request: SignInRequest
    ): Response<SignInResponse>

    @POST("api/v1/projects")
    suspend fun createProject(
        @Body request: CreateProjectRequest
    ): Response<CreateProjectResponse>

    @GET("api/v1/projects")
    suspend fun getProjects(): Response<ProjectsResponse>
}