package com.fov.domain.models.authentication.users

data class DeleteAccountDTO(
    val  userId: String,
    val  reason: String,
    val  password:  String
)
