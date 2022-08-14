package com.fov.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fov.core.di.Preferences
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.User
import com.fov.main.events.MainEvent
import com.fov.main.states.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: Preferences,
    private val userDao: UserDao
) :  ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()


    init {

             viewModelScope.launch {

                 sharedPreferences.isVerified?.collectLatest { isVer ->
                     _uiState.value = uiState.value.build {
                         if(isVer != null)
                             isVerified = isVer
                     }
                 }
                 sharedPreferences.accessToken?.let { token ->
                     token.collectLatest{ it ->
                         if(it != null && it != "") {

                             userDao.getUser().collectLatest{ usr ->
                                 _uiState.value = uiState.value.build {
                                     isAuthenticated = true
                                     user = usr
                                 }
                             }

                         }
                         else{
                             _uiState.value = uiState.value.build {
                                 isAuthenticated = false
                             }
                         }
                     }
               }
           }
    }
    fun handleMainEvent(event : MainEvent){
        _uiState.value = uiState.value.build {
            when (event) {


                is MainEvent.SearchTextChanged -> {
                    search = event.search
                }
                is MainEvent.LoadLocation  -> {
                    location =  event.location
                }

                else -> {

                }
            }
        }
    }




}

