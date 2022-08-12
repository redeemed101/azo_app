package com.fov.domain.models.authentication.users

data class DisableAccountDTO(
    val  userId: String,
    val  reason: String,
    val  password:  String
)
