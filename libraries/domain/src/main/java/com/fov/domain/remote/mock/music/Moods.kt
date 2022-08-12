package com.fidarr.domain.remote.mock.music

import com.fidarr.domain.models.music.playlist.Mood
import com.fidarr.domain.models.music.playlist.MoodResult
import com.google.gson.Gson

object MoodsMockResponse {
    operator fun invoke(): String {
        val obj = MoodResult(
            moods = List(20){ num ->
                Mood(
                    dateCreated = "",
                    id = "$num",
                    imagePath = "https://picsum.photos/id/${num * 10}/200",
                    name = "Mood$num",
                    shortName = "$num"

                )

            }
        )

       return Gson().toJson(obj)
    }
}