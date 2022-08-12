package com.fov.domain.remote.authentication


import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.login.*
import com.fov.domain.models.authentication.password.PasswordChangeRequest
import com.fov.domain.models.authentication.password.ResetPasswordRequest
import com.fov.domain.models.authentication.registration.*
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.models.users.notifications.NotificationsResult
import com.fov.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthenticationKtorService constructor(private val client: HttpClient) {


    suspend fun logout(): Void = client.request("users/User/logout") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun socialSignin(signinRequest: SocialMediaLoginRequest): SigninResult =
        client.request("users/User/socialSignin") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = signinRequest

        }

    suspend fun signUp(signupRequest: SignupRequest): SignupResult =
        client.request("users/User/signup") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = signupRequest

        }

    suspend fun resetPassword(request: ResetPasswordRequest): SigninResult =
        client.request("users/User/reset") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = request
        }

    suspend fun resendCode(userId: String, reset: Boolean): GeneralResult =
        client.request("users/User/resendUserCode?isReset=$reset") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = userId
        }

    suspend fun changePassword(request: PasswordChangeRequest): GeneralResult =
        client.request("users/User/passwordchange") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = request
        }

    suspend fun verifyUserCode(request: VerifyCodeRequest): GeneralResult =
        client.request("users/User/verifyUserCode") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = request
        }

    suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResult =
        client.request("users/Token/refresh") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = request
        }

    suspend fun signIn(request: SigninRequest, restore: Boolean): SigninResult =
        client.request("users/User/signin?restore=$restore") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = request
        }

    suspend fun forgotPassword(email: String): GeneralResult =
        client.request("users/User/forgotPassword") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = email
        }


    suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult? {
        var url = "users/User/notifications/$userId?page=$page&size=${QueryConstants.NUM_ROWS}"
        if (unread)
            url =
                "users/User/notifications/unread/$userId?page=$page&size=${QueryConstants.NUM_ROWS}"
        return client.request(url) {
            method = HttpMethod.Get
            headers {
                append("Content-Type", "application/json")
            }
        }
    }

    suspend fun getNumberUnreadNotifications(id: String): Int  =  client.request("users/User/numNotifications/$id") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }

    }

    suspend  fun deleteAccount(delete: DeleteAccountDTO) : GeneralResult =
        client.request("users/User/deleteAccount") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = delete
        }
    suspend  fun disableAccount(disable: DisableAccountDTO) : GeneralResult =
        client.request("users/User/disableAccount") {
            method = HttpMethod.Post
            headers {
                append("Content-Type", "application/json")
            }
            body = disable
        }
}