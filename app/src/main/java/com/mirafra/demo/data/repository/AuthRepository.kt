package com.mirafra.demo.data.repository

import com.mirafra.demo.data.remote.model.request.SignInRequest
import com.mirafra.demo.data.remote.model.response.SignInResponse

interface AuthRepository {
    suspend fun signIn(request: SignInRequest): Result<SignInResponse>
}