package com.mirafra.demo.data.remote.interceptor

import com.mirafra.demo.data.local.datastore.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // If 401 received, clear token and return null to cancel request
        // (later you can add refresh token logic here)
        runBlocking { tokenDataStore.clearToken() }
        return null  // returning null stops the request → triggers logout flow
    }
}