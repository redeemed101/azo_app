package com.fov.domain.remote.mock.news

import com.fov.domain.models.general.ImagePager
import com.fov.domain.models.general.ImagePagerResult
import com.fov.domain.models.news.MainNewsImage
import com.fov.domain.models.news.News
import com.fov.domain.models.news.NewsImage
import com.fov.domain.models.news.NewsResult
import com.google.gson.Gson
object ImagePagerMockResponse {
    operator fun invoke(): String {
        var obj = ImagePagerResult(
            images = List(20){
                ImagePager(
                    id = "1111 $it",
                    path = "https://picsum.photos/200",
                    description = "This is $it",
                    createdAt = "2023-02-01",
                    show = true,

                )
            }
        )
        return Gson().toJson(obj)
    }
}
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