package com.mirafra.demo.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val emailId: String,
    val password: String
)