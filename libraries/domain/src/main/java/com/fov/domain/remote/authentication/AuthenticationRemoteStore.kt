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
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull

class AuthenticationRemoteStore constructor(
    private val authenticationService: AuthenticationService,
   ) : AuthenticationRemote {
    override suspend fun signUp(
        userName: String,
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        countryId: Int
    ): SignupResult? {
        var result : SignupResult? = null
        authenticationService.signUp(
            SignupRequest(
                userName = userName,
                fullName = fullName,
                email= email,
                password = password,
                phoneNumber = phoneNumber,
                countryId = countryId
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun signIn(
        emailAddress: String,
        password: String,
        restore: Boolean
    ): SigninResult? {
        var result : SigninResult? = null
        
        authenticationService.signIn(
            SigninRequest(
                username = emailAddress,
                password = password
            ),
            restore
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }
    override suspend fun socialSignIn(
        fullName: String,
        username: String,
        emailAddress: String,
        service: String,
        token:String,
        profileImageUrl : String?,
        isFirstTime : Boolean
    ): SigninResult? {
        var result : SigninResult? = null

        authenticationService.socialSignin(
            SocialMediaLoginRequest(
                userName = username,
                email = emailAddress,
                service = service,
                fullName = fullName,
                token = token,
                isFirstTime = isFirstTime,
                profileImageUrl = profileImageUrl
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }



    override suspend fun logout() {
        authenticationService.logout()
    }

    override suspend fun forgotPassword(email: String): GeneralResult? {
        var result : GeneralResult? = null
        authenticationService.forgotPassword(email).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun resendCode(userId: String, isRestore : Boolean): GeneralResult? {
        var result : GeneralResult? = null
        authenticationService.resendCode(userId,isRestore).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }


    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String,
        restore:Boolean
    ): SigninResult? {
        var result : SigninResult? = null
        authenticationService.resetPassword(
            ResetPasswordRequest(
                email = email,
                code = code,
                password = password
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun changePassword(
        username: String,
        newPassword: String,
        oldPassword: String
    ): GeneralResult? {
        var result : GeneralResult? = null
        authenticationService.changePassword(
            PasswordChangeRequest(
                username = username,
                newPassword = newPassword,
                oldPassword = oldPassword
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun verifyUserCode(userId: String, code: String): GeneralResult? {
       var result : GeneralResult? = null
        authenticationService.verifyUserCode(
            VerifyCodeRequest(
                userId = userId,
                code = code
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun refreshToken(token: String, refreshToken: String): RefreshTokenResult? {
        var result : RefreshTokenResult? = null
        authenticationService.refreshToken(
            RefreshTokenRequest(
                token = token,
                refreshToken = refreshToken
            )
        ).suspendOnSuccess {
            data.whatIfNotNull { res ->
                result = res
            }
        }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }
        return result
    }

    override suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult? {
        var result : NotificationsResult? = null
        if(unread) {
            authenticationService.getUserUnreadNotifications("", page, QueryConstants.NUM_ROWS)
                .suspendOnSuccess {
                  data.whatIfNotNull { res ->
                    result = res
                  }
                }
                .onError {
                    throw Exception(message())
                }
                .onException {
                    throw Exception(message())
                }
        }
        else{
            authenticationService.getUserNotifications(userId, page, QueryConstants.NUM_ROWS)
                .suspendOnSuccess {
                    data.whatIfNotNull { res ->
                        result = res
                    }
                }
                .onError {
                    throw Exception(message())
                }
                .onException {
                    throw Exception(message())
                }
        }

        return result

    }

    override suspend fun getNumberUnreadNotifications(id: String): Int {
        var result = 0

        authenticationService.getNumberUnreadNotifications(id)
            .suspendOnSuccess {
                data.whatIfNotNull { res ->
                    result = res
                }
            }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }

        return result
    }

    override suspend fun deleteAccount(delete: DeleteAccountDTO): GeneralResult? {
        var result : GeneralResult? = null

        authenticationService.deleteAccount(delete)
            .suspendOnSuccess {
                data.whatIfNotNull { res ->
                    result = res
                }
            }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }

        return result
    }

    override suspend fun disableAccount(disable: DisableAccountDTO): GeneralResult? {
        var result : GeneralResult? = null

        authenticationService.disableAccount(disable)
            .suspendOnSuccess {
                data.whatIfNotNull { res ->
                    result = res
                }
            }
            .onError {
                throw Exception(message())
            }
            .onException {
                throw Exception(message())
            }

        return result
    }
}