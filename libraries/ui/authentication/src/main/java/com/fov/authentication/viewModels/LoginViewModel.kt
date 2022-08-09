package com.fov.authentication.viewModels

import androidx.lifecycle.ViewModel
import com.fov.authentication.events.LoginEvent
import com.fov.authentication.states.LoginState
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

//@HiltViewModel
class LoginViewModel@Inject constructor(

) : ViewModel()
{
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    private var _toast: MutableStateFlow<String> = MutableStateFlow("")
    val toast: StateFlow<String> get() = _toast
    init{

    }
    fun handleRegistrationEvent(event: LoginEvent) {
        _uiState.value = uiState.value.build {
            when (event) {

                else -> {

                }
            }
        }
    }

}