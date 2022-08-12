package com.fov.domain.database.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Immutable
@Serializable
data class RecentActivity(
    @PrimaryKey(autoGenerate = true) val dbId: Long = Long.MIN_VALUE,
    val id : String,
    val title : String,
    val subTitle : String,
    val type : String,
    val image : String
)
enum class ActivityType(val type: String) {
    SONG("SONG"),
    ARTIST("ARTIST"),
    ALBUM("ALBUM")
}