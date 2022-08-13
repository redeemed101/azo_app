package com.fov.authentication.events

import com.fov.authentication.models.UserModel

sealed class UsersEvent {
    data class LoadFlickersToFollow(val search : String = "") : UsersEvent()
    data class FollowUser(val user : String) : UsersEvent()
    data class SaveRecentUserSearch(val user : UserModel) : UsersEvent()
    object ClearRecentUserSearch : UsersEvent()
    data class UserSelected(val userId : String) : UsersEvent()
    data class UnFollowUser(val userId : String) : UsersEvent()
    data class SearchUserTextChanged(val search : String) : UsersEvent()
    data class SearchUser(val search : String) : UsersEvent()
    object LoadNotifications : UsersEvent()
    object SaveUser : UsersEvent()
    object LogoutUser : UsersEvent()
}