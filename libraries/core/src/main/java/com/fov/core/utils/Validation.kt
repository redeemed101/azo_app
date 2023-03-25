package com.fov.core.utils

import android.text.TextUtils

object Validation {

    fun isEmail(email : String) : Boolean{
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    fun isValidPassword(password : String) : Boolean{
        val regex = "^.*(?=.{6,})((?=.*[!@#\$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1}).*\$".toRegex()
        return regex.matches(password);
    }

    fun isTokenValid(token : String) : Boolean{
        return true;
    }
}