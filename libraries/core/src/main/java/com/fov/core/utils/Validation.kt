package com.fov.core.utils

object Validation {

    fun isEmail(email : String) : Boolean{
        return true;
    }
    fun isValidPassword(password : String) : Boolean{
        val regex = "^.*(?=.{6,})((?=.*[!@#\$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1}).*\$".toRegex()
        return regex.matches(password);
    }

    fun isTokenValid(token : String) : Boolean{
        return true;
    }
}