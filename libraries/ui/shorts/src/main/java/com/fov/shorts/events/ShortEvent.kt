package com.fov.shorts.events

import com.fov.domain.models.shorts.Short

sealed class ShortEvent{
    object ShortsLoaded : ShortEvent()
    object ReloadShorts : ShortEvent()

    data class ShortSelected(val short: Short) : ShortEvent()
    data class ToggleShowShort(val show: Boolean) : ShortEvent()
}
