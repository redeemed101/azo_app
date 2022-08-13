package com.fov.authentication.states

import androidx.paging.PagingData
import com.fov.authentication.models.UserModel
import com.fov.authentication.models.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UsersState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success : String? = null,
    val userModel: UserModel? = null,
    val followingListPaged : Flow<PagingData<UserModel>>? = null,
    val notifications : Flow<PagingData<Notification>>? = null,
    val recentUserSearch: Flow<PagingData<UserModel>> = flowOf(PagingData.from(emptyList())),
    val searchUserText : String = "",
    val numNotifications : Int = 0,
    val userProfileId : String = "",
    val currentUserModel: UserModel? =  null,
    val editUserModel : UserModel? = null,
    val isEditUserContentValid : Boolean = false,
    val  isEditPersonalInformationValid : Boolean =false,
    val logoutDone: Boolean = false
){


    companion object {
        fun initialise(): UsersState = UsersState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: UsersState) {
        var loading = state.isLoading
        var error = state.errorMessage
        var success = state.success
        var userModel=state.userModel
        var currentUserModel=state.currentUserModel
        var followingListPaged = state.followingListPaged
        var notifications = state.notifications
        var recentUserSearch = state.recentUserSearch
        var searchUserText = state.searchUserText
        var numNotifications = state.numNotifications
        var  userProfileId = state.userProfileId
        var editUserModel = state.editUserModel
        var isEditUserContentValid = state.isEditUserContentValid
        var isEditPersonalInformationValid = state.isEditPersonalInformationValid
        var logoutDone = state.logoutDone
        fun build(): UsersState {
            return UsersState(
                loading,
                error,
                success,
                userModel,
                followingListPaged,
                notifications,
                recentUserSearch,
                searchUserText,
                numNotifications,
                userProfileId,
                currentUserModel,
                editUserModel,
                isEditUserContentValid,
                isEditPersonalInformationValid,
                logoutDone
            )
        }
    }
}