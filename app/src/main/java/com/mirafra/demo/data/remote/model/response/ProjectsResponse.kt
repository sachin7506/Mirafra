package com.mirafra.demo.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ProjectsResponse(
    val status: String,
    val message: String,
    val projects: List<Project>
)