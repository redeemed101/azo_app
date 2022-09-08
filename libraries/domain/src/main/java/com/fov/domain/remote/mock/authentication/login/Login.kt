package com.fov.domain.remote.mock.authentication.login

import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.login.SigninResult
import com.fov.domain.models.authentication.registration.SignupResult
import com.google.gson.Gson


object LogoutMockResponse{
    operator fun invoke(): String {
        val response = GeneralResult(
            success = true,
            message = "successfully logged out"
        )
        return Gson().toJson(response)
    }
}
object LoginMockResponse{
    operator fun invoke(): String {
        val response = SigninResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = User(
                name = "Lewis Msasa",
                privateKey = "123432w343",
                publicKey = "44322433",
                profilePicPath = "",
                id = "sdfsdfsdf",
                email = "lmsasajnr@d.com",
                profile = ""
            )
        )
        return Gson().toJson(response)
    }
}
object SocialLoginMockResponse{
    operator fun invoke(): String {
        val response = SigninResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = User(
                name = "Lewis Msasa",
                privateKey = "123432w343",
                publicKey = "44322433",
                profilePicPath = "",
                id = "sdfsdfsdf",
                email = "lmsasajnr@d.com",
                profile = ""
            )
        )
        return Gson().toJson(response)
    }
}