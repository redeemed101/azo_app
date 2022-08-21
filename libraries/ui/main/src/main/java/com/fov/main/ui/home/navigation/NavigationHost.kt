package com.fov.main.ui.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.screens.*
import com.fov.main.viewModels.MainViewModel
import com.fov.navigation.AuthenticationDirections
import com.fov.navigation.HomeDirections
import com.fov.navigation.SermonsDirections
import com.fov.sermons.ui.MusicPlayerScreen
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import kotlinx.coroutines.InternalCoroutinesApi


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    InternalCoroutinesApi::class
)
@Composable
fun NavigationHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    musicViewModel: SermonViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
) {
    NavHost(
        navController,
        startDestination = AuthenticationDirections.mainTab.destination,

        ) {

        navigation(
            startDestination = SermonsDirections.genres.destination,
            route = AuthenticationDirections.mainTab.destination
        ) {

            /*composable(
                HomeDirections.home.destination,
                arguments = HomeDirections.home.arguments
            ) {

                Home(
                    commonViewModel,
                    musicViewModel,
                    usersViewModel
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
            }*/

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
                SermonsDirections.song.destination,
                arguments = SermonsDirections.song.arguments
            ) {
                SongScreen(
                    musicViewModel = musicViewModel, commonViewModel = commonViewModel,
                    storedMusicViewModel = storedMusicViewModel,
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

        }
    }
}