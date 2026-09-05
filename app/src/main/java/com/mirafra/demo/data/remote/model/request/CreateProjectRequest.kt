package com.mirafra.demo.data.remote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectRequest(
    val name: String,
    val objective: String,
    @SerialName("analysis_focus")
    val analysisFocus: String? = null
)