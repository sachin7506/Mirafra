package com.mirafra.demo.data.remote.model.response


import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val status: String,
    val message: String,
    val accessToken: String,
    val tokenType: String,
    val user: User
)

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val emailId: String,
    val status: String,
    val onboardingCompleted: Boolean
)
