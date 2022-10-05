package com.fov.domain.remote.authentication

import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.login.*
import com.fov.domain.models.authentication.password.PasswordChangeRequest
import com.fov.domain.models.authentication.password.ResetPasswordRequest
import com.fov.domain.models.authentication.registration.*
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.models.users.notifications.NotificationsResult
import com.fov.domain.utils.extensions.toCustomExceptions
import com.fov.domain.remote.authentication.AuthenticationKtorService
import com.fov.domain.remote.authentication.AuthenticationRemote

class AuthenticationRemoteKtorStore constructor(
    private val authenticationService: AuthenticationKtorService,
) : AuthenticationRemote {
    override suspend fun signUp(
        userName: String,
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
    ): SignupResult? {
        return authenticationService.signUp(
            SignupRequest(
                userName = userName,
                fullName = fullName,
                email= email,
                password = password,
                phoneNumber = phoneNumber,
            )
        )
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
        return authenticationService.socialSignin(
            SocialMediaLoginRequest(
                userName = username,
                email = emailAddress,
                service = service,
                fullName = fullName,
                token = token,
                isFirstTime = isFirstTime,
                profileImageUrl = profileImageUrl,
            )
        )
    }

    override suspend fun signIn(
        emailAddress: String,
        password: String,
        restore: Boolean
    ): SigninResult? {
        return authenticationService.signIn(
            SigninRequest(
                username = emailAddress,
                password = password
            ),
            restore
        )
    }



    override suspend fun logout() {
        authenticationService.logout()
    }

    override suspend fun forgotPassword(email: String): GeneralResult? {
        return authenticationService.forgotPassword(email)
    }

    override suspend fun resendCode(userId: String, isRestore: Boolean): GeneralResult? {
       return authenticationService.resendCode(userId,isRestore)
    }



    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String,
        restore: Boolean
    ): SigninResult? {
       return  authenticationService.resetPassword(
           ResetPasswordRequest(
               email = email,
               code = code,
               password = password
           )
       )
    }

    override suspend fun changePassword(
        username: String,
        newPassword: String,
        oldPassword: String
    ): GeneralResult? {

        return authenticationService.changePassword(
            PasswordChangeRequest(
                username = username,
                newPassword = newPassword,
                oldPassword = oldPassword
            )
        )
    }

    override suspend fun verifyUserCode(userId: String, code: String): GeneralResult? {
        try {
            return authenticationService.verifyUserCode(
                VerifyCodeRequest(
                    userId = userId,
                    code = code
                )
            )
        }
        catch(e:Exception){
            throw Exception(e.toCustomExceptions())
        }
    }

    override suspend fun refreshToken(token: String, refreshToken: String): RefreshTokenResult? {
       return authenticationService.refreshToken(
           RefreshTokenRequest(
               token = token,
               refreshToken = refreshToken
           )
       )
    }

    override suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult? {
        return authenticationService.getUserNotifications(userId,page, unread)
    }

    override suspend fun getNumberUnreadNotifications(id: String): Int {
        return authenticationService.getNumberUnreadNotifications(id)
    }

    override suspend fun deleteAccount(delete: DeleteAccountDTO) = authenticationService.deleteAccount(delete)
    override suspend fun disableAccount(disable: DisableAccountDTO) = authenticationService.disableAccount(disable)
}