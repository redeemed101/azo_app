package com.fov.shorts.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.core.di.Preferences
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.shorts.events.ShortEvent
import com.fov.shorts.paging.dataSources.ShortsDataSource
import com.fov.shorts.states.ShortState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.fov.domain.models.shorts.Short
import kotlinx.coroutines.flow.*

@HiltViewModel
class ShortViewModel @Inject constructor(
    private val videoInteractor: VideoInteractor,
    private val sharedPreferences: Preferences
) :  ViewModel(){
    private val _uiState = MutableStateFlow(ShortState())
    val uiState: StateFlow<ShortState> = _uiState
    private var accessToken: String? = null


    init {

        viewModelScope.launch {
            try {
                sharedPreferences.accessToken?.let { token ->
                    token.collectLatest { it ->
                        if(it != null) {
                            accessToken = it
                            if(accessToken != null) {
                                val shortsResult = Pager(PagingConfig(pageSize = 20)) {
                                    ShortsDataSource(videoInteractor, accessToken!!)
                                }.flow
                                _uiState.value = uiState.value.build {
                                    shorts = shortsResult
                                }
                            }
                        }
                    }
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
                ShortsDataSource(videoInteractor, accessToken!!)
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