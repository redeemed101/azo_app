package com.fov.domain.remote.mock.authentication.registration


import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.GeneralResult
import com.fov.domain.models.authentication.registration.SignupResult
import com.fov.domain.models.authentication.users.UserDTO
import com.google.gson.Gson

object SignUpMockResponse{
    operator fun invoke(): String {
        val response = SignupResult(
            success = true,
            token = "dsfsdafdsgsdfgefgrgdfbvsfdfgfdgsdfs",
            refreshToken = "sdfsadfdsgfdgdfgf",
            user = User(
               name = "Lewis Msasa",
               privateKey = "123432w343",
               publicKey = "44322433",
               profilePicPath = "",
               id = "sdfsdfsdf",
               userName = "lmsasajnr",
               email = "lmsasajnr@d.com",
               profile = ""
            )
        )
        return Gson().toJson(response)
    }
}

object GeneralMockResponse{
    operator fun invoke() : String {
        val response = GeneralResult(
           success = true,
           message = "Successfully Done"
        )
        return Gson().toJson(response)
    }
}
