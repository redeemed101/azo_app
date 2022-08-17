package com.example.fidarrappcompose.utils.helpers

import android.content.Context
import androidx.compose.ui.graphics.Color
import kotlin.random.Random


object Utilities {

    val  DOWNLOAD_URL : String  = "download_url"
    val  DOWNLOAD_DESTINATION_FILE : String  = "download_destination_file"
    val DOWNLOAD_DETAILS : String = "download_details"

    fun downloadFile(url: String, context : Context){

    }


    fun randomColor() : Color {


        return Color(red = Random.nextInt(256),
            green = Random.nextInt(256),
            blue = Random.nextInt(256),
            //alpha = Random.nextInt(256)
        )
    }
}