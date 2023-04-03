package com.fov.domain.remote

import android.util.Log
import com.fov.core.di.Preferences
import com.fov.domain.BuildConfig
import com.fov.domain.models.authentication.login.RefreshTokenRequest
import com.fov.domain.models.authentication.login.RefreshTokenResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AuthInterceptor constructor(private val sharedPrefs: Preferences): Interceptor {

    init{

    }
    private fun callApi(originalRequest: Request,jsonData : String, token: String) : Response{

            val client = OkHttpClient
                          .Builder()
                          .addInterceptor(RequestInterceptor())
                          .build();

            val body = jsonData.toRequestBody("application/json".toMediaTypeOrNull());
            val request =  originalRequest.newBuilder()
                .url("${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/Token/refresh")
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            return call.execute()

    }
    private suspend fun refreshToken(chain: Interceptor.Chain,
                                     initialRequest: Request,
                                     token: String?,
                                     refreshToken: String?): Response {
         if(!token.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
             var refreshRequest = RefreshTokenRequest(token,refreshToken)
             val gson = Gson()
             var json = gson.toJson(refreshRequest)
             var response = callApi(initialRequest,json,token)
             // make an API call to get new token
             if (response != null) {
                     val resString = response.body?.string()
                     response.close()
                     Log.d("AuthInterceptor",resString.toString())

                 var refreshResponse = gson.fromJson(resString,RefreshTokenResult::class.java)
                 if (!refreshResponse.token.isNullOrEmpty()) {
                     sharedPrefs.setAuthToken(refreshResponse.token)
                     sharedPrefs.setRefreshToken(refreshResponse.refreshToken)

                     val newRequest = initialRequest
                         .newBuilder()
                         .header("Authorization", "Bearer ${refreshResponse.token}")
                         .build()
                     return chain.proceed(newRequest)
                 }
             }
         }
        return chain.proceed(initialRequest)
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sharedPrefs.accessToken.firstOrNull()
        };
        val refreshToken = runBlocking {
            sharedPrefs.refreshToken.firstOrNull()
        };
        val initialRequest = chain.request()
        if (!token.isNullOrEmpty()) {
            val authRequest = initialRequest
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(authRequest)
            if (response.code == 401) {
                runBlocking { // this: CoroutineScope
                    response.close()
                    return@runBlocking refreshToken(chain, authRequest, token, refreshToken)

                }
            } else {
                return response
            }
        }
        return chain.proceed(initialRequest)
    }
}