package com.fov.common_ui.mock.data

import com.fov.common_ui.models.NewsModel

val NEWS = List(20){
       NewsModel(
           "HIH Zambia",
           "/data/data/com.fov.azo/files/apostle2.jpg",
            listOf(
                   "/data/data/com.fov.azo/files/apostle.jpg",
                   "https://picsum.photos/200"
            ),
           "Heaven Is Here Zambia is coming soon",
           "https://www.google.com"

       )
}