package com.fov.authentication.states

import androidx.paging.PagingData
import com.fov.authentication.models.UserModel
import com.fov.core.utils.Validation
import com.fov.domain.database.models.User
import kotlinx.coroutines.flow.Flow

class RegistrationState(
    val userId : String = "",
    val username : String = "",
    val isUsernameValid : Boolean = false,
    val fullname : String = "",
    val isFullnameValid : Boolean = false,
    val email: String = "",
    val isEmailValid : Boolean = false,
    val password: String = "",
    val isPasswordValid : Boolean = false,
    val confirmPassword : String = "",
    val isConfirmValid : Boolean = false,
    val phoneNumber : String = "",
    val isPhoneValid : Boolean = false,
    val countryId : Int = 0,
    val isCountryValid:Boolean = false,
    val isRegistrationContentValid : Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null,
    var newFollowing : MutableList<UserModel> = mutableListOf(),
    val followingList : List<User> = listOf(),
    val followingListPaged : Flow<PagingData<UserModel>>? = null,
    val searchToFollow : String = String(),
    val verificationCode : String = String(),
    val successMessage : String = String(),
    val user: User? = null,
    val socialMediaService : String = "",
    val socialMediaToken : String = "",
    val socialMediaFirstTime : Boolean = false,
    val verificationDone : Boolean = false,
    val profileImageUrl : String? = null,
    val isVerifyCodeContentValid : Boolean = false
) {
    companion object {
        fun initialise(): RegistrationState = RegistrationState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: RegistrationState) {
        var userId = state.userId
        var username = state.username
        var isUsernameValid = state.isUsernameValid
        var fullname = state.fullname
        var  isFullnameValid = state.isFullnameValid
        var email = state.email
        var isEmailValid = state.isEmailValid
        var newFollowing = state.newFollowing
        var followingList = state.followingList
        var followingListPaged = state.followingListPaged
        var searchToFollow = state.searchToFollow
        var verificationCode = state.verificationCode
        var password = state.password
        var isPasswordValid = state.isPasswordValid
        var confirmPassword = state.confirmPassword
        var isConfirmPasswordValid = state.isConfirmValid
        var phoneNumber = state.phoneNumber
        var isPhoneValid = state.isPhoneValid
        var countryId = state.countryId
        var isCountryValid = state.isCountryValid
        var isRegistrationContentValid = state.isRegistrationContentValid
        var loading = state.isLoading
        var success = state.success
        var error = state.errorMessage
        var successMessage = state.successMessage
        var user = state.user
        var socialMediaService = state.socialMediaService
        var socialMediaToken = state.socialMediaToken
        var socialMediaFirstTime = state.socialMediaFirstTime
        var verificationDone = state.verificationDone
        var profileImageUrl = state.profileImageUrl
        var isVerifyCodeContentValid = state.isVerifyCodeContentValid

         fun valid() : Boolean{
            var result : Boolean = true

                 result = username.trim().isNotEmpty()

                 result = fullname.trim().isNotEmpty()

                 result = email.trim().isNotEmpty() && Validation.isEmail(email)

                 result = password.trim().isNotEmpty()

                 result = confirmPassword.trim().isNotEmpty() && confirmPassword.trim().equals(password.trim())

            return result
        }

        fun build(): RegistrationState {
            return RegistrationState(
                userId,
                username,
                isUsernameValid,
                fullname,
                isFullnameValid,
                email,
                isEmailValid,
                password,
                isPasswordValid,
                confirmPassword,
                isConfirmPasswordValid,
                phoneNumber,
                isPhoneValid,
                countryId,
                isCountryValid,
                isRegistrationContentValid,
                loading,
                success,
                error,
                newFollowing,
                followingList,
                followingListPaged,
                searchToFollow,
                verificationCode,
                successMessage,
                user,
                socialMediaService,
                socialMediaToken,
                socialMediaFirstTime,
                verificationDone,
                profileImageUrl,
                isVerifyCodeContentValid
            )
        }
    }
}

