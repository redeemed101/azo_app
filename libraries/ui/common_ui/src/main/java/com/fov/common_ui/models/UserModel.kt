package com.fov.common_ui.models

import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.users.UserDTO
import com.fov.domain.users.GetUserQuery

data class UserModel(
    val name: String,
    val email : String,
    val profile:String,
    val profilePic: String = "",
    val phoneNumber:String = "",
    val id: String,
){
    object ModelMapper {
        fun fromUser(user : User, ) =
            UserModel(
                name = user.name,
                email = user.email,
                profile = user.profile,
                profilePic = user.profilePicPath,
                id = user.id,
                phoneNumber = "",

            )
        fun fromUserDTO(user : UserDTO, ) =
            UserModel(
                name = user.fullName,
                email = user.email,
                profile = "",
                profilePic = "",
                id = user.id,
                phoneNumber = user.phoneNumber,

            )
        fun fromGraph(user : GetUserQuery.Data) =
            UserModel(
                name = user.user!!.fullName,
                email = user.user!!.email,
                profile = user.user!!.profile,
                profilePic = user.user!!.profilePicPath,
                id = user.user!!.id,
            )
    }
}
