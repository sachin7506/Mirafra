package com.mirafra.demo.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectResponse(
    val status: String,
    val message: String,
    val project: Project
)

@Serializable
data class Project(
    val id: String,
    val userId: String,
    @SerialName("projectName")
    val projectName: String,
    val objective: String,
    val analysisFocus: String? = null,
    val status: String,
    val aiReviewStatus: String,
    val sourceCount: Int,
    val findingCount: Int,
    val questionCount: Int,
    val decisionCount: Int
)