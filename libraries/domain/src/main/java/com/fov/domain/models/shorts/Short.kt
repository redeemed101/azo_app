package com.fov.domain.models.shorts

import androidx.annotation.Keep
import com.fov.domain.BuildConfig
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Short(
    @SerializedName("name") val name: String,
    @SerializedName("content") var content : String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type : String,
    @SerializedName("createdAt") val createdAt : String,
){
    object ModelMapper {
        fun withFullUrls(short : Short) : Short{
            if (short.type == ShortType.IMAGE.name){
                short.content = "${BuildConfig.URL_PROTOCOL}${BuildConfig.FOV_URL}/${short.content}"
            }
            return short
        }
    }
}
