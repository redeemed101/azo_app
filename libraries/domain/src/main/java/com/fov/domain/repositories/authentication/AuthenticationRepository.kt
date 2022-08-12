package com.fov.domain.repositories.authentication

import androidx.paging.PagingSource
import com.fov.domain.database.models.RecentUserSearch
import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.login.RefreshTokenResult
import com.fov.domain.models.authentication.login.SigninResult
import com.fov.domain.models.authentication.registration.SignupResult
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.models.users.notifications.NotificationsResult
import com.fov.domain.remote.authentication.AuthenticationRemoteStore
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun signUp(
        userName : String,
        fullName : String,
        email : String,
        password : String,
        phoneNumber : String,
        countryId: Int
    ): SignupResult?


    suspend fun socialSignIn(
        fullName: String,
        username: String,
        emailAddress: String,
        service: String,
        token:String,
        profileImageUrl : String?,
        isFirstTime : Boolean
    ): SigninResult?

    suspend fun signIn(
        emailAddress: String,
        password: String,
        restore : Boolean
    ): SigninResult?



    suspend fun logout()
    suspend fun forgotPassword(email : String) : GeneralResult?

    suspend fun resendCode(userId : String, isRestore:Boolean) : GeneralResult?

    suspend fun resetPassword(
        email : String,
        code : String,
        password: String,
        restore : Boolean
    ) : SigninResult?

    suspend fun changePassword(
        username : String,
        newPassword : String,
        oldPassword: String
    ): GeneralResult?

    suspend fun verifyUserCode(
        userId : String,
        code : String
    ): GeneralResult?

    suspend fun refreshToken(
        token : String,
        refreshToken : String
    ) : RefreshTokenResult?


    suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean): NotificationsResult?
    suspend fun getNumberUnreadNotifications(id: String) : Int
    suspend fun deleteAccount(delete: DeleteAccountDTO): GeneralResult?
    suspend fun disableAccount(disable: DisableAccountDTO): GeneralResult?
}