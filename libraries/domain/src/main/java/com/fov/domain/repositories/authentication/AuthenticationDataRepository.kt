package com.fov.domain.repositories.authentication

import com.fov.domain.cache.Cache
import com.fov.domain.database.models.RecentUserSearch
import com.fov.domain.models.authentication.login.SigninResult
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.remote.authentication.AuthenticationRemote
import com.fov.domain.repositories.authentication.AuthenticationRepository

class AuthenticationDataRepository constructor(
    private val authenticationRemote : AuthenticationRemote,
    private val cache: Cache
) : AuthenticationRepository
{
    override suspend fun signUp(
        userName: String,
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        countryId: Int
    ) = authenticationRemote.signUp(
        userName,
        fullName,
        email,
        password,
        phoneNumber,
        countryId
    )

    override suspend fun socialSignIn(
        fullName: String,
        username: String,
        emailAddress: String,
        service: String,
        token: String,
        profileImageUrl : String?,
        isFirstTime: Boolean
    ): SigninResult? = authenticationRemote.socialSignIn(
        fullName,
        username,
        emailAddress,
        service,
        token,
        profileImageUrl,
        isFirstTime
    )
    override suspend fun signIn(
        emailAddress: String,
        password: String,
        restore: Boolean
    ) = authenticationRemote.signIn(
        emailAddress,
        password,
        restore
    )


    override suspend fun logout() = authenticationRemote.logout()

    override suspend fun forgotPassword(email: String) = authenticationRemote.forgotPassword(email)
    override suspend fun resendCode(userId: String, isRestore: Boolean) = authenticationRemote.resendCode(userId,isRestore)


    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String,
        restore : Boolean
    ) = authenticationRemote.resetPassword(
        email,
        code,
        password,
        restore
    )

    override suspend fun changePassword(
        username: String,
        newPassword: String,
        oldPassword: String
    ) = authenticationRemote.changePassword(
        username,
        newPassword,
        oldPassword
    )

    override suspend fun verifyUserCode(userId: String, code: String)
    = authenticationRemote.verifyUserCode(userId,code)

    override suspend fun refreshToken(token: String, refreshToken: String)
    = authenticationRemote.refreshToken(token,refreshToken)

    override suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean) = authenticationRemote.getUserNotifications(userId,page, unread)



    override suspend fun getNumberUnreadNotifications(id: String) = authenticationRemote.getNumberUnreadNotifications(id)
    override suspend fun deleteAccount(delete: DeleteAccountDTO) = authenticationRemote.deleteAccount(delete)
    override suspend fun disableAccount(disable: DisableAccountDTO) =  authenticationRemote.disableAccount(disable)

}