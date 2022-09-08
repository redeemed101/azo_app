package com.fov.azo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fidarrappcompose.utils.helpers.LocalSysUiController
import com.example.fidarrappcompose.utils.helpers.SystemUiController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import com.facebook.LoginStatusCallback
import com.facebook.login.LoginManager
import com.fov.authentication.events.RegistrationEvent
import com.fov.authentication.ui.login.Login
import com.fov.authentication.ui.registration.registration.Onboarding
import com.fov.authentication.ui.registration.registration.Registration
import com.fov.authentication.ui.registration.registration.VerifyAccount
import com.fov.authentication.viewModels.LoginViewModel
import com.fov.authentication.viewModels.RegistrationViewModel
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.ui.composers.general.EnterAnimation
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.utils.helpers.NotificationUtils
import com.fov.core.di.Preferences
import com.fov.navigation.AuthenticationDirections
import com.fov.navigation.GeneralDirections
import com.fov.navigation.NavigationManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONException
import java.lang.Exception
import javax.inject.Inject


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private val registrationViewModel: RegistrationViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()

    @Inject
    lateinit var navigationManager : NavigationManager

    @Inject
    lateinit var sharedPrefs : Preferences


    private val RC_SIGN_IN = 1

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class,
        androidx.compose.foundation.ExperimentalFoundationApi::class,
        androidx.compose.ui.ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        //notification channel
        NotificationUtils.createNotificationChannel(this)


        super.onCreate(savedInstanceState)



        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {

                    AzoTheme {

                        if (ThemeHelper.isDarkTheme())
                            window.statusBarColor = MaterialTheme.colors.onPrimary.toArgb()
                        else
                            window.statusBarColor = MaterialTheme.colors.primary.toArgb()

                        val token by sharedPrefs.accessToken.collectAsState(initial = null)
                        val isVerified by sharedPrefs.isVerified.collectAsState(initial = false)


                        val context = LocalContext.current

                        val navController = rememberNavController()

                        var startDestination = AuthenticationDirections.onboarding.destination




                        navigationManager.commands.collectAsState().value.also{ command ->
                            if(command.destination.isNotEmpty()){
                                if(command.destination == GeneralDirections.back.destination) {

                                    navController.navigateUp()

                                }
                                else{
                                    LoadingBox()
                                    var destination = command.destination
                                    val dest = intent.getStringExtra("destination")
                                    if(dest != null){
                                        destination = dest
                                    }
                                    navController.navigate(destination)

                                }

                            }

                        }

                        EnterAnimation {

                            LoadingBox()
                        }






                        NavHost(navController,
                            startDestination = startDestination) {

                            composable(
                                AuthenticationDirections.onboarding.destination
                            ) {
                                EnterAnimation {
                                    Onboarding(registrationViewModel)
                                }


                            }

                            composable(AuthenticationDirections.verifyAccount.destination) {
                                EnterAnimation {
                                    VerifyAccount(
                                        viewModel = registrationViewModel
                                    )
                                }
                            }
                            composable(AuthenticationDirections.login.destination) {
                                EnterAnimation {
                                    Login(
                                        loginViewModel = loginViewModel
                                    )
                                }
                            }
                            composable(AuthenticationDirections.registration.destination) {
                                EnterAnimation{

                                    Registration(
                                        viewModel = registrationViewModel
                                    )
                                }
                            }
                        }

                    }

            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            val context = applicationContext
            registrationViewModel.uiState.collect{ state ->
                if(state.followingDone){
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    context.startActivity(intent)
                    finish()
                    this.cancel()
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val context = applicationContext
            loginViewModel.uiState.collect{ state ->
                if(state.loginDone){
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    context.startActivity(intent)
                    finish()
                    this.cancel()
                }
            }

        }

        CoroutineScope(Dispatchers.Main).launch {
            sharedPrefs.accessToken.collectLatest { token ->
                sharedPrefs.isVerified.collectLatest { verified ->
                    if((verified == false || verified == null) && token != null){
                        navigationManager.navigate(AuthenticationDirections.verifyAccount)
                    }
                }
            }
            this.cancel()
        }
    }

    override fun onStart() {


        super.onStart()

        var  events = registrationViewModel::handleRegistrationEvent

    }

}