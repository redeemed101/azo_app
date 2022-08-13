package com.fov.authentication.models

import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.users.UserDTO
import com.fov.domain.users.GetUserQuery

data class UserModel(
    val name: String,
    val email : String,
    val phoneNumber:String = "",
    val id: String,
    val isEmailConfirmed: Boolean = false,
){
    object ModelMapper {
        fun fromUser(user : User, ) =
            UserModel(
                name = user.name,
                email = user.email,
                id = user.id,
                phoneNumber = "",
            )
        fun fromUserDTO(user : UserDTO, ) =
            UserModel(
                name = user.fullName,
                email = user.email,
                id = user.id,
                phoneNumber = user.phoneNumber,
                isEmailConfirmed = user.emailConfirmed,

                )
        fun toUserDTO(user :  UserModel)  = UserDTO(
            fullName = user.name,
            email = user.email,
            id = user.id,
            phoneNumber = user.phoneNumber,
            emailConfirmed = user.isEmailConfirmed,
            privateKey = "",
            publicKey = ""
        )

        fun fromGraph(user : GetUserQuery.Data) =
            UserModel(
                name = user.user!!.fullName,
                email = user.user!!.email,
                id = user.user!!.id,
                phoneNumber = user.user!!.phoneNumber,
            )
    }
}
