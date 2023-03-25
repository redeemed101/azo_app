package com.fov.common_ui.utils.constants

class Constants {

    companion object {
        val MAINTAB = "mainTab"

        val NUM_PAGE = 5

        val DOWNLOAD_URL: String = "download_url"
        val FILE_EXTENSION: String = "file_extension"
        val DIRECTORY : String = "directory"
        val HOW_OLD_DAYS = "days"
        val DOWNLOAD_IMAGE_URL: String = "download_image_url"
        val DOWNLOAD_DESTINATION_FILE: String = "download_destination_file"
        val DOWNLOAD_DETAILS: String = "download_details"
        val DOWNLOAD_FILE_ENCRYPTION_KEY = "encryption_key"
    }
}

enum class UsersRequestType {
    USERS_TO_FOLLOW, ALL_USERS, RECENT_SEARCHED
}

enum class SongRequestType {
    TOP_SONGS, ALL_SONGS,GENRE_SONGS, FOR_YOU,RECENT_SEARCH, RECENT, LIKED_SONGS, YEAR_SONGS
}

enum class AlbumRequestType {
    TOP_ALBUMS, ALL_ALBUMS, GENRE_ALBUMS,LIKED_ALBUMS
}

