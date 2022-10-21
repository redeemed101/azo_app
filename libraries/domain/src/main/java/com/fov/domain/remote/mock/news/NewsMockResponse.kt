package com.fov.domain.remote.mock.news

import com.fov.domain.models.news.MainNewsImage
import com.fov.domain.models.news.News
import com.fov.domain.models.news.NewsImage
import com.fov.domain.models.news.NewsResult
import com.google.gson.Gson

object NewsMockResponse {
    operator fun invoke(): String {
        var obj = NewsResult(
            news = List(20){
                News(
                    id = "$it",
                    title = "News $it",
                    createdAt = "2022-10-22",
                    mainImage = MainNewsImage(
                         id = "$it",
                         newsId = "$it",
                         path = "https://picsum.photos/200"
                    ),
                    images = List(4){
                        NewsImage(
                            id = "$it",
                            newsId = "$it",
                            path = "https://picsum.photos/200"
                        )
                    },
                    publishDate = "2022-10-22",
                    url = "www.google.com",
                    content = "A good story"
                )
            }
        )
        return Gson().toJson(obj)
    }
}