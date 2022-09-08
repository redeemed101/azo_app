package com.fov.authentication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fov.authentication.events.LoginEvent
import com.fov.authentication.states.LoginState
import com.fov.core.di.Preferences
import com.fov.core.utils.Validation
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.User
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.models.authentication.login.SigninResult
import com.fov.navigation.AuthenticationDirections
import com.fov.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences,
    private val userDao: UserDao,
    private val navigationManager: NavigationManager
) : ViewModel()
{
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    private var _toast: MutableStateFlow<String> = MutableStateFlow("")
    val toast: StateFlow<String> get() = _toast
    init{
        viewModelScope.launch {
            var usr = userDao.getUser().collect { user ->
                if(user == null){
                    _uiState.value = uiState.value.build {
                        this.isRestore = true
                    }
                }
            }

        }
    }
    fun handleRegistrationEvent(event: LoginEvent) {
        _uiState.value = uiState.value.build {
            when (event) {
                LoginEvent.DismissErrorDialog -> {
                    this.error = null
                }
                is LoginEvent.EmailChanged -> {
                    this.userEmail = event.email
                }
                LoginEvent.ForgotPasswordClicked -> {
                    navigationManager.navigate(AuthenticationDirections.forgotPassword)
                }
                LoginEvent.LoginClicked -> {
                    if(isLoginContentValid) {
                        login {
                            this.loginDone = true
                        }
                    }
                    else
                        error = "Please fill all fields"
                }
                LoginEvent.SocialLoginClicked -> {
                    //socialLogin()
                }
                is LoginEvent.PasswordChanged -> {
                    this.userPassword = event.password
                }
                is LoginEvent.SocialMediaServiceChanged-> {
                    this.socialMediaService = event.service
                }
                is LoginEvent.SocialMediaTokenChanged-> {
                    this.socialMediaToken = event.token
                }
                is LoginEvent.SocialMediaIsFirstTimeChanged-> {
                    this.socialMediaFirstTime = event.isFirstTime
                }
                LoginEvent.RegisterClicked -> {
                    navigationManager.navigate(AuthenticationDirections.registration)
                }
                else -> {

                }
            }
            _uiState.value = uiState.value.build {
                isLoginContentValid = userEmail.trim().isNotEmpty() &&
                        userPassword.trim().isNotEmpty() && Validation.isEmail(userEmail.trim())
            }
        }
    }
    private fun login(doneLoggingOut : () -> Unit){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val res = authenticate.signIn(
                    emailAddress = _uiState.value.emailAddress,
                    password =  _uiState.value.password,
                    restore = _uiState.value.isRestore
                )
                processResponse(res,_uiState.value.isRestore)
                if (res != null) {
                    if(res.success){
                        doneLoggingOut()
                        _uiState.value = uiState.value.build {
                            loginDone = true
                        }
                    }
                }

            }
            catch(ex : Exception){

            }
        }
    }
    private suspend fun processResponse(res : SigninResult?, toStore : Boolean){
        if(res != null){
            if(res.success){
                if(toStore){
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
                //navigationManager.navigate(HomeDirections.home)
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Wrong credentials. Please try again"
                }
                sharedPrefs.setAuthToken("")
            }
        }
        else {
            _uiState.value = uiState.value.build {
                loading = false
                error = "Error login. Try again"
            }
        }
    }

}