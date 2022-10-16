package com.fov.common_ui.mock.data

import com.fov.common_ui.models.NewsModel

val NEWS = List(20){
       NewsModel(
           "HIH Zambia",
           "https://picsum.photos/200",
            listOf(
                   "https://picsum.photos/200",
                   "https://picsum.photos/200"
            ),
           "Heaven Is Here Zambia is coming soon",
           "https://www.google.com"

       )
}