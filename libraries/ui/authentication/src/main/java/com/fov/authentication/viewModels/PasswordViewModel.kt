package com.fov.authentication.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fov.authentication.events.PasswordEvent
import com.fov.authentication.states.PasswordState
import com.fov.core.di.Preferences
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.User
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.navigation.AuthenticationDirections
import com.fov.navigation.HomeDirections
import com.fov.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordViewModel@Inject constructor(
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences,
    private val userDao: UserDao,
    private val navigationManager: NavigationManager
) : ViewModel(){
    private val _uiState = MutableStateFlow(PasswordState())
    val uiState: StateFlow<PasswordState> = _uiState

    init {
        viewModelScope.launch {
            var usr = userDao.getUser().collect { user ->
                if(user == null){
                    _uiState.value = uiState.value.build {
                         this.isRestore = true
                    }
                }
                _uiState.value = uiState.value.build {
                    this.user  = user
                }

            }

        }
    }
    fun handleRegistrationEvent(event: PasswordEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                is PasswordEvent.ConfirmNewPasswordChanged -> {
                    this.confirmNewPassword = event.password
                }
                PasswordEvent.DismissErrorDialog -> {
                    this.error = null
                }
                PasswordEvent.ForgotPasswordClicked -> {
                      forgotPassword()
                }
                is PasswordEvent.NewPasswordChanged -> {
                    this.newPassword = event.password
                }
                PasswordEvent.ResendVerificationCode -> {
                    if(this.user != null)
                        resendCode()
                }
                PasswordEvent.ResetPasswordClicked -> {
                      resetPassword()
                }
                is PasswordEvent.VerificationCodeChanged -> {
                    this.verificationCode = event.code
                }
            }
        }
    }
    private fun resetPassword(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val res = authenticate.resetPassword(
                    _uiState.value.email,
                    _uiState.value.verificationCode,
                    _uiState.value.newPassword,
                    _uiState.value.isRestore
                )
                if(res != null) {

                    if (res.success) {
                        if(_uiState.value.isRestore){
                            userDao.insertAll(
                                User(
                                    name = res.user.name,
                                    privateKey =  res.user.privateKey,
                                    publicKey = res.user.publicKey,
                                    id = res.user.id,
                                    email = res.user.email,
                                    profile = res.user.profile
                                )
                            )

                        }
                        sharedPrefs.setAuthToken(res.token)
                        navigationManager.navigate(HomeDirections.home)
                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = "Failed to reset Password"
                        }

                    }
                }
                else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Error resetting password. Try again"
                    }
                }
            }
            catch(ex: Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Error resetting password. Try again"
                }
            }
        }
    }
    private fun forgotPassword(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val res = authenticate.forgotPassword(_uiState.value.email)
                if(res != null) {

                    if (res.success) {

                        navigationManager.navigate(AuthenticationDirections.resetPassword)
                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = res.message
                        }

                    }
                }
                else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Error resetting password. Try again"
                    }
                }
            }
            catch(ex: Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Error resetting password. Try again"
                }
            }
        }
    }
    private fun resendCode(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val res = authenticate.resendCode(_uiState.value.user!!.id,true)
                if(res != null) {

                    if (res.success) {

                        _uiState.value = uiState.value.build {
                            this.successMessage = "Code successfully resent"
                        }
                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = res.message
                        }

                    }
                }
                else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Error resending code. Try again"
                    }
                }
            }
            catch(ex: Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Error resending code. Try again"
                }
            }
        }
    }
}