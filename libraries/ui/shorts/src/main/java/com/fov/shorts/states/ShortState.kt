package com.fov.shorts.states

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.fov.domain.models.shorts.Short

class ShortState(
    val loading : Boolean = true,
    val shorts : Flow<PagingData<Short>>? = null,
    val short : Short? = null,
    val isShowingShort: Boolean = false,
) {
    companion object {
        fun initialise(): ShortState = ShortState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: ShortState) {
        var loading = state.loading
        var shorts = state.shorts
        var isShowingShort = state.isShowingShort
        var short = state.short
        fun build(): ShortState {
            return ShortState(
                loading,
                shorts,
                short,
                isShowingShort,

            )
        }
    }
}