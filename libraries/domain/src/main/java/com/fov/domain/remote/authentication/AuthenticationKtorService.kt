package com.fov.domain.remote.authentication


import android.util.Log
import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.ResendCodeRequest
import com.fov.domain.models.authentication.login.*
import com.fov.domain.models.authentication.password.PasswordChangeRequest
import com.fov.domain.models.authentication.password.ResetPasswordRequest
import com.fov.domain.models.authentication.registration.*
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.models.users.notifications.NotificationsResult
import com.fov.domain.utils.constants.QueryConstants
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthenticationKtorService constructor(private val client: HttpClient) {


    suspend fun logout(): GeneralResult {
        val res : HttpResponse = client.request("User/logout") {
            method = HttpMethod.Get
            headers {
                append("Content-Type", "application/json")
            }
        }
        val status = res.status
        return res.body()
    }

    suspend fun socialSignin(signinRequest: SocialMediaLoginRequest): SigninResult =
        client.request("User/socialSignin") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(signinRequest)

        }.body()

    suspend fun signUp(signupRequest: SignupRequest): SignupResult? {
        val response = client.request("User/signup") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(signupRequest)

        }
        Log.d("Response for signup",response.bodyAsText())
        return response.body()
    }

    suspend fun resetPassword(request: ResetPasswordRequest): SigninResult =
        client.request("User/reset") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }.body()

    suspend fun resendCode(userId: String, reset: Boolean): GeneralResult {
        val request = ResendCodeRequest(userId = userId)
        var response = client.request("UserCode/resendUserCode?isReset=$reset") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }
        return response.body()
    }

    suspend fun changePassword(request: PasswordChangeRequest): GeneralResult =
        client.request("User/passwordchange") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }.body()

    suspend fun verifyUserCode(request: VerifyCodeRequest): GeneralResult =
        client.request("UserCode/verifyUserCode") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }.body()

    suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResult =
        client.request("Token/refresh") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }.body()


    suspend fun signIn(request: SigninRequest, restore: Boolean): SigninResult =
        client.request("User/signin?restore=$restore") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(request)
        }.body()

    suspend fun forgotPassword(email: String): GeneralResult =
        client.request("User/forgotPassword") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(email)
        }.body()


    suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult? {
        var url = "User/notifications/$userId?page=$page&size=${QueryConstants.NUM_ROWS}"
        if (unread)
            url =
                "User/notifications/unread/$userId?page=$page&size=${QueryConstants.NUM_ROWS}"
        return client.request(url) {
            method = HttpMethod.Get
            headers {
                append("Content-Type", "application/json")
            }
        }.body()
    }

    suspend fun getNumberUnreadNotifications(id: String): Int  =  client.request("users/User/numNotifications/$id") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }

    }.body()

    suspend  fun deleteAccount(delete: DeleteAccountDTO) : GeneralResult =
        client.request("User/deleteAccount") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(delete)
        }.body()
    suspend  fun disableAccount(disable: DisableAccountDTO) : GeneralResult =
        client.request("User/disableAccount") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            setBody(disable)
        }.body()

    suspend fun sendDeviceToken(token: String): GeneralResult? =
        client.request("DeviceToken/saveToken") {
        method = HttpMethod.Post
        headers {
            append("Content-Type", "application/json")
        }
        setBody(token)
    }.body()
}