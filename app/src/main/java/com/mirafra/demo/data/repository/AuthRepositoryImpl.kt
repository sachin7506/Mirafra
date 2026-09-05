package com.mirafra.demo.data.repository

import com.mirafra.demo.data.local.datastore.TokenDataStore
import com.mirafra.demo.data.remote.api.ApiService
import com.mirafra.demo.data.remote.model.request.SignInRequest
import com.mirafra.demo.data.remote.model.response.SignInResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun signIn(request: SignInRequest): Result<SignInResponse> {
        return try {
            val response = apiService.signIn(request)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenDataStore.saveToken(body.accessToken)
                Result.success(body)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}