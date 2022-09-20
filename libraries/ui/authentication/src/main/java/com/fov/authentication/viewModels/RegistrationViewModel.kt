package com.fov.authentication.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.authentication.events.RegistrationEvent
import com.fov.common_ui.utils.constants.UsersRequestType
import com.fov.authentication.states.RegistrationState
import com.fov.common_ui.utils.constants.Constants
import com.fov.core.di.Preferences
import com.fov.core.security.encryption.KeyGeneration
import com.fov.core.utils.Validation
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.User
import com.fov.domain.exceptions.ServerException
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.models.authentication.registration.SignupResult
import com.fov.navigation.AuthenticationDirections
import com.fov.navigation.HomeDirections
import com.fov.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val keyGeneration: KeyGeneration,
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences,
    private val userDao: UserDao,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState: StateFlow<RegistrationState> = _uiState

    private var _toast: MutableStateFlow<String> = MutableStateFlow("")
    val toast: StateFlow<String> get() = _toast

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()

    init {
        viewModelScope.launch {

        }
    }

    fun handleRegistrationEvent(event: RegistrationEvent) {
        if (event is RegistrationEvent.RegistrationClicked) {
            _uiState.value = uiState.value.build {
                loading = true
            }
            if(_uiState.value.isRegistrationContentValid) {
                register()
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Please  fill all fields"
                }
            }
        }
        else {
            _uiState.value = uiState.value.build {
                when (event) {

                    RegistrationEvent.DismissErrorDialog -> {
                        this.error = null
                    }
                    RegistrationEvent.RegistrationNavigationClicked ->{
                        navigationManager.navigate(AuthenticationDirections.registration)
                    }
                    RegistrationEvent.RegistrationClicked -> {
                        this.loading = true
                    }

                    RegistrationEvent.LoginClicked -> {
                        navigationManager.navigate(
                            AuthenticationDirections.login
                        )
                    }
                    RegistrationEvent.SocialRegisterClicked -> {
                        //socialLogin()
                    }
                    RegistrationEvent.VerifyCodeClicked -> {
                        submitVerificationCode{
                            verificationDone = true
                        }
                    }
                    is RegistrationEvent.EmailChanged -> {

                            this.email = event.email
                            this.isEmailValid =
                                Validation.isEmail(event.email) && event.email.isNotEmpty()


                    }
                    is RegistrationEvent.PasswordChanged -> {
                        this.password = event.password
                    }
                    is RegistrationEvent.FullNameChanged -> {
                        this.fullname = event.fullname
                    }
                    is RegistrationEvent.ProfileUrlChanged -> {
                        profileImageUrl = event.url
                    }
                    is RegistrationEvent.SocialMediaServiceChanged-> {
                        this.socialMediaService = event.service
                    }
                    is RegistrationEvent.SocialMediaTokenChanged-> {
                        this.socialMediaToken = event.token
                    }
                    is RegistrationEvent.SocialMediaIsFirstTimeChanged-> {
                        this.socialMediaFirstTime = event.isFirstTime
                    }

                    is RegistrationEvent.UsernameChanged -> {
                        this.username = event.username

                    }
                    is RegistrationEvent.ConfirmPasswordChanged -> {
                        this.confirmPassword = event.confirmPassword
                    }

                    is RegistrationEvent.PhoneNumberChanged -> {
                        this.phoneNumber = event.phoneNumber
                    }

                    is RegistrationEvent.LoadUser -> {
                       _uiState.value.build{
                           user = event.user
                       }
                    }

                    is RegistrationEvent.VerificationCodeChanged -> {
                        this.verificationCode = event.code
                    }

                    RegistrationEvent.ResendVerificationCode -> {
                        if(this.user != null)
                            resendCode()
                        else{
                                error = "No user found"

                        }
                    }
                }
            }
        }
        _uiState.value = uiState.value.build {
            this.isRegistrationContentValid =
                    this.fullname.trim().isNotEmpty() && this.email.trim().isNotEmpty() && Validation.isEmail(this.email)
                    && password.trim().isNotEmpty() && confirmPassword.trim().isNotEmpty()
                    && confirmPassword.trim() == password.trim()
        }

    }



    private fun submitVerificationCode(doneVerifying : () -> Unit) {
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {

                  val result =   authenticate.verifyUserCode(
                        _uiState.value.user?.id ?: "",
                        _uiState.value.verificationCode
                    )

                if(result != null){
                    if(result.success){
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = null
                        }
                        sharedPrefs.setIsVerified(true)
                        navigationManager.commands = MutableStateFlow(HomeDirections.Default)
                        _uiState.value = uiState.value.build {
                            verificationDone = true
                        }
                        doneVerifying()


                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = result.message
                        }
                        //sharedPrefs.setAuthToken("")
                        //sharedPrefs.setRefreshToken("")
                    }
                }
                else{
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Failed to verify code"
                    }
                    //sharedPrefs.setAuthToken("")
                    //sharedPrefs.setRefreshToken("")
                    //userDao.deleteAll()
                }
            }
            catch (ex : Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
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
                            loading = false
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

    private suspend fun processRegistrationResult(result : SignupResult?){
        if (result != null) {
            if(result.success) {
                var usr =  User(
                    name = result.user.name,
                    privateKey =  result.user.privateKey,
                    publicKey = result.user.publicKey,
                    id = result.user.id,
                    profile = result.user.profile,
                    email = result.user.email
                )
                userDao.insertAll(
                    usr
                )
                _uiState.value.build {
                    this.user = usr
                }
                sharedPrefs.setAuthToken(result.token)
                sharedPrefs.setRefreshToken(result.refreshToken)
                sharedPrefs.setIsVerified(false)

                //secret key
                val secretKey = keyGeneration.generateSecretKey()

                if(secretKey != null)
                  keyGeneration.saveSecretKey(secretKey,viewModelScope)


                navigationManager.navigate(AuthenticationDirections.verifyAccount)
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Failed to register"
                }
            }

        } else {
            _uiState.value = uiState.value.build {
                loading = false
                error = "Error registering. Try again"
            }
        }
    }
    private fun register() {
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val result = authenticate.signUp(
                    userName = _uiState.value.username,
                    fullName = _uiState.value.fullname,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    phoneNumber = _uiState.value.phoneNumber,
                    countryId = _uiState.value.countryId
                )
                processRegistrationResult(result)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = null
                }
            }
            catch(ex : ServerException){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
            catch (ex : Exception){

                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
        }
    }
}