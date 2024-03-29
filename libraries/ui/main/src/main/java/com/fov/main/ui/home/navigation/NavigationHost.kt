package com.fov.main.ui.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.ui.composers.general.WebView
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.home.WebViewScreen
import com.fov.main.ui.sermons.audio.screens.*
import com.fov.main.viewModels.MainViewModel
import com.fov.sermons.ui.MusicPlayerScreen
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import com.fov.main.ui.home.screens.Home
import com.fov.main.ui.home.screens.NotificationScreen
import com.fov.main.ui.home.screens.SearchScreen
import com.fov.main.ui.news.screens.NewsHome
import com.fov.main.ui.payment.screens.ActivationCodeScreen
import com.fov.main.ui.payment.screens.PaymentOptionsScreen
import com.fov.main.ui.payment.screens.StripeScreen
import com.fov.main.ui.profile.screens.UserProfileScreen
import com.fov.main.ui.sermons.library.screen.LibraryHomeScreen
import com.fov.main.ui.sermons.video.screens.VideoHomeScreen
import com.fov.navigation.*
import com.fov.payment.viewModels.PaymentViewModel
import com.fov.shorts.viewModels.ShortViewModel


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    InternalCoroutinesApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class
)
@Composable
fun NavigationHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    musicViewModel: SermonViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
    shortViewModel: ShortViewModel,
    paymentViewModel: PaymentViewModel,
    playVideo : (id:String) -> Unit
) {

    NavHost(
        navController,
        modifier = Modifier.zIndex(-1f),
        startDestination = AuthenticationDirections.mainTab.destination,

        ) {

        navigation(
            startDestination =  HomeDirections.home.destination,
            route = AuthenticationDirections.mainTab.destination
        ) {

            composable(
                HomeDirections.home.destination,
                arguments = HomeDirections.home.arguments
            ) {

                Home(
                    commonViewModel,
                    musicViewModel,
                    usersViewModel,
                    shortViewModel
                )
                /*PaymentOptionsScreen(
                    commonViewModel = commonViewModel,
                    paymentViewModel = paymentViewModel )*/

            }


            composable(
                HomeDirections.profile.destination,
                arguments = HomeDirections.profile.arguments
            ) {

                UserProfileScreen(
                    usersViewModel,
                    commonViewModel,
                    musicViewModel,
                    storedMusicViewModel,
                    paymentViewModel
                )
            }




            composable(
                HomeDirections.notifications.destination,
                arguments = HomeDirections.notifications.arguments
            ) {

                NotificationScreen(
                    commonViewModel,
                    usersViewModel
                )
            }
            composable(
                HomeDirections.search.destination,
                arguments = HomeDirections.search.arguments

            ) {

                SearchScreen(
                    commonViewModel = commonViewModel,
                    mainViewModel = mainViewModel,
                    usersViewModel = usersViewModel,
                    musicViewModel = musicViewModel
                )
            }
            composable(
                HomeDirections.webview.destination,
                arguments = HomeDirections.webview.arguments
            ) {

              WebViewScreen(
                  commonViewModel
              )


            }

            //video
            composable(
                VideoDirections.tab.destination,
                arguments = VideoDirections.tab.arguments
            ) {
                Column {
                    VideoHomeScreen(
                        musicViewModel,
                        commonViewModel,
                        playVideo
                    )

                }
               

            }

            //music
            composable(
                SermonsDirections.tab.destination,
                arguments = SermonsDirections.tab.arguments
            ) {

                MusicHomeScreen(
                    musicViewModel,
                    commonViewModel,
                )


            }
            composable(
                SermonsDirections.genres.destination,
                arguments = SermonsDirections.genres.arguments
            ) {


                GenresScreen(
                    musicViewModel = musicViewModel,
                    commonViewModel = commonViewModel
                )


            }
            composable(
                SermonsDirections.genre.destination,
                arguments = SermonsDirections.genre.arguments
            ) {


                GenreScreen(
                    musicViewModel = musicViewModel,
                    commonViewModel = commonViewModel,
                )


            }

            composable(
                SermonsDirections.years.destination,
                arguments = SermonsDirections.years.arguments
            ) {


                YearsScreen(
                    musicViewModel = musicViewModel,
                    commonViewModel = commonViewModel
                )


            }

            composable(
                SermonsDirections.year.destination,
                arguments = SermonsDirections.year.arguments
            ) {


                ByYearScreen(
                    musicViewModel = musicViewModel,
                    commonViewModel = commonViewModel,
                )


            }

            composable(
                SermonsDirections.song.destination,
                arguments = SermonsDirections.song.arguments
            ) {
                SongScreen(
                    musicViewModel = musicViewModel, commonViewModel = commonViewModel,
                    storedMusicViewModel = storedMusicViewModel
                )
            }

            composable(
                SermonsDirections.album.destination,
                arguments = SermonsDirections.album.arguments
            ) {
                AlbumScreen(
                    sermonsViewModel = musicViewModel,
                    commonViewModel = commonViewModel,
                    storedSermonViewModel = storedMusicViewModel,
                )
            }
            composable(
                SermonsDirections.playSong.destination,
                arguments = SermonsDirections.album.arguments
            ) {
                MusicPlayerScreen(
                    sermonViewModel = musicViewModel,
                    commonViewModel = commonViewModel
                )
            }

            //library
            composable(
                SermonsDirections.downloaded_tab.destination,
                arguments = SermonsDirections.downloaded_tab.arguments
            ) {

                LibraryHomeScreen(
                    musicViewModel = musicViewModel,
                    storedSermonViewModel = storedMusicViewModel,
                    commonViewModel = commonViewModel
                )


            }

            //news
            composable(
                HomeDirections.news.destination,
                arguments = HomeDirections.news.arguments
            ) {

               NewsHome(commonViewModel = commonViewModel,
                   sermonViewModel = musicViewModel)


            }

            //payment
            composable(
                PaymentDirections.options.destination,
                arguments = PaymentDirections.options.arguments
            ) {


                PaymentOptionsScreen(
                    commonViewModel = commonViewModel,
                    paymentViewModel = paymentViewModel )

            }
            composable(
                PaymentDirections.stripe.destination,
                arguments = PaymentDirections.stripe.arguments
            ) {


                StripeScreen(commonViewModel = commonViewModel,
                    paymentViewModel = paymentViewModel)

            }
            composable(
                PaymentDirections.code.destination,
                arguments = PaymentDirections.code.arguments
            ) {


                ActivationCodeScreen(commonViewModel = commonViewModel,
                    paymentViewModel = paymentViewModel)

            }


        }
    }
}