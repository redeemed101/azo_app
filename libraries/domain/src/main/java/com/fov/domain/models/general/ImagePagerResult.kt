package com.fov.domain.models.general

import kotlinx.serialization.Serializable

@Serializable
data class ImagePagerResult(
   val images : List<ImagePager> = emptyList()
)
