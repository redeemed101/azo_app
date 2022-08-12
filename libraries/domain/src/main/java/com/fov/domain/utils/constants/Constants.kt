package com.fov.domain.utils.constants

object QueryConstants {
    val NUM_ROWS = 5
}



enum class NotificationType(val type: String) {
    FOLLOW("FOLLOW"),
    COMMENT("COMMENT"),
    LIKE("LIKE")
}