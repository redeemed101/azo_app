package com.fov.azo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private val registrationViewModel: RegistrationViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()
    private var account: GoogleSignInAccount? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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
                                        loginViewModel = loginViewModel,
                                        OnGoogleSignIn = {
                                            onGoogleSignIn()
                                        },
                                    )
                                }
                            }
                            composable(AuthenticationDirections.registration.destination) {
                                EnterAnimation{

                                    Registration(
                                        viewModel = registrationViewModel,
                                        OnGoogleSignIn = {
                                            onGoogleSignIn()
                                        }
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
               if(state.verificationDone){
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

        account = GoogleSignIn.getLastSignedInAccount(this)
        super.onStart()
        if(account != null) {
            handleGoogleLogin(account!!)
        }
    }

    private fun handleGoogleLogin( account: GoogleSignInAccount){

            var  events = registrationViewModel::handleRegistrationEvent
            account!!.email?.let {
                events(RegistrationEvent.EmailChanged(it))
            }
            account!!.displayName?.let {
                events(RegistrationEvent.FullNameChanged("${it}"))
            }
            events(RegistrationEvent.UsernameChanged(""))
            events(RegistrationEvent.SocialMediaServiceChanged("GOOGLE"))
            account!!.idToken?.let {
                events(RegistrationEvent.SocialMediaTokenChanged(it))
            }
            Log.d("Login", "we are in handle Login")
            events(RegistrationEvent.SocialMediaIsFirstTimeChanged(true))
            events(RegistrationEvent.SocialRegisterClicked)

    }

    private val getGoogleLoginResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            Log.d("Login", "within register for results ${it.data?.data} ${it.resultCode}")
            if(it.resultCode != RESULT_CANCELED){
                 val data = it.data
                Log.d("Login", "data ${it.data.toString()}")
                 val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                 account = task.getResult(ApiException::class.java)
                if(account != null) {
                    Log.d("Login", "Account ${account!!.email}")
                    handleGoogleLogin(account!!)
                }

            }
        }

    fun onGoogleSignIn() {
        mGoogleSignInClient?.signInIntent?.let {
            val signInIntent: Intent = it
            Log.d("Login","${it.data}")
             getGoogleLoginResult.launch(signInIntent)

        };

    }

}