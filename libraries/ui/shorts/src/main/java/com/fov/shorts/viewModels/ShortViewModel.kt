package com.fov.shorts.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.domain.models.shorts.ShortItem
import com.fov.shorts.events.ShortEvent
import com.fov.shorts.paging.dataSources.ShortsDataSource
import com.fov.shorts.states.ShortState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.fov.domain.models.shorts.Short

@HiltViewModel
class ShortViewModel @Inject constructor(
   private val videoInteractor: VideoInteractor
) :  ViewModel(){
    private val _uiState = MutableStateFlow(ShortState())
    val uiState: StateFlow<ShortState> = _uiState


    init {

        viewModelScope.launch {
            try {
                val shortsResult = Pager(PagingConfig(pageSize = 20)) {
                    ShortsDataSource(videoInteractor)
                }.flow
                _uiState.value = uiState.value.build {
                    shorts = shortsResult
                }
            }
            catch (e: Exception){
                Log.e("StoriesError",e.message.toString())
            }
        }
    }
    fun reloadStories(){
        try {
            val shortsResult = Pager(PagingConfig(pageSize = 20)) {
                ShortsDataSource(videoInteractor)
            }.flow
            _uiState.value = uiState.value.build {
                shorts = shortsResult

            }
        }
        catch (e: Exception){
            Log.e("StoriesError",e.message.toString())
        }
    }
    fun handleMainEvent(event : ShortEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                ShortEvent.ShortsLoaded-> {

                }
                ShortEvent.ReloadShorts -> {

                }
                is ShortEvent.ShortSelected -> {
                     short = event.short
                }
                is ShortEvent.ToggleShowShort -> {
                    this.isShowingShort = event.show
                }
            }
        }
    }

}