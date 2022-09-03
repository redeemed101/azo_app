package com.fov.domain.models.shorts


import kotlinx.serialization.Serializable


@Serializable
data class ShortItem(
    val id : String,
    val content : String,
    val description:String = "",
    val dateCreated : String
)
