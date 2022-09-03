package com.fov.azo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fidarrappcompose.utils.helpers.LocalSysUiController
import com.example.fidarrappcompose.utils.helpers.SystemUiController
import com.facebook.CallbackManager
import com.fov.authentication.viewModels.RegistrationViewModel
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.core.di.Preferences
import com.fov.main.ui.home.MainTabUI
import com.fov.main.viewModels.MainViewModel
import com.fov.navigation.GeneralDirections
import com.fov.navigation.NavigationManager
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import com.fov.shorts.viewModels.ShortViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.youtube.player.YouTubeBaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var account: GoogleSignInAccount? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val commonViewModel: CommonViewModel by viewModels()
    private val shortViewModel : ShortViewModel by viewModels()
    private val musicViewModel: SermonViewModel by viewModels()
    private val registrationViewModel: RegistrationViewModel by viewModels()
    private val usersViewModel : UsersViewModel by viewModels()
    private val storedMusicViewModel : StoredSermonViewModel by viewModels()

    @Inject
    lateinit var navigationManager : NavigationManager

    @Inject
    lateinit var sharedPrefs : Preferences
    var callbackManager: CallbackManager? = null
    @OptIn(ExperimentalFoundationApi::class, InternalCoroutinesApi::class,
        ExperimentalAnimationApi::class, ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val navController = rememberNavController()
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {

                AzoTheme {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.statusBarColor = MaterialTheme.colors.onSurface.toArgb()
                    }


                    navigationManager.commands.collectAsState().value.also{ command ->
                    if(command.destination.isNotEmpty()){
                        if(command.destination == GeneralDirections.back.destination) {

                            navController.navigateUp()
                            navigationManager.commands.value = GeneralDirections.Default

                        }
                        else{
                            LoadingBox()
                            var destination = command.destination


                            navController.navigate(destination)

                        }

                    }

                }
                   val context = LocalContext.current
                   val videoPlay = { videoId : String ->
                       var intent = Intent(context, YouTubePlayerActivity::class.java)
                       intent.putExtra("videoId",videoId)
                       context.startActivity(intent)
                   }

                    Box(
                        modifier = Modifier.systemBarsPadding()
                    ){
                        MainTabUI(
                            navController,
                            mainViewModel,
                            musicViewModel,
                            usersViewModel,
                            commonViewModel,
                            shortViewModel,
                            storedMusicViewModel,
                            videoPlay
                        ){
                            navigationManager.navigate(it)
                        }
                    }
                }
            }
        }
    }
}

