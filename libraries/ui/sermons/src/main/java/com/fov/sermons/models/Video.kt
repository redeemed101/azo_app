package com.fov.sermons.models

data class Video(
    val videoId : String,
    val genres: List<String>? = listOf(),
    val videoName : String,
    val artwork : String,
    val artistName: String = "Apostle Ziba",
    val description : String = "",
    //val path : String = "",
){
    object ModelMapper {

    }
}
