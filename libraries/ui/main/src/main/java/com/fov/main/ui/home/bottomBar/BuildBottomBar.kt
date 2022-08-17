package com.fov.main.ui.home.bottomBar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.DarkGrey
import com.fov.common_ui.theme.ThemeHelper
import com.fov.navigation.BackPageData
import com.fov.navigation.NavigationCommand
import com.fov.navigation.Screen
import com.fov.navigation.tabItems
import com.fov.sermons.events.MusicEvent

@Composable
fun BuildBottomBar(
    commonState: CommonState,
    events : (CommonEvent) -> Unit,
    musicEvents :  (MusicEvent) -> Unit,
    navigate : (route : NavigationCommand) -> Unit,
    currentTab : String
) : @Composable () -> Unit{
    return {

        if(commonState.hasBottomBar){
            var backgroundColor = MaterialTheme.colors.surface
            if (ThemeHelper.isDarkTheme())
                backgroundColor = MaterialTheme.colors.surface
            var selectedItemColor = MaterialTheme.colors.primary
            if (ThemeHelper.isDarkTheme()) {
                selectedItemColor = MaterialTheme.colors.onSurface
            }

            BottomNavigation(
                backgroundColor = backgroundColor,
                contentColor = DarkGrey
            ) {


                tabItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {

                                Icon(
                                    painter = painterResource(screen.iconResourceId!!),
                                    ""
                                )

                        },
                        label = { Text(stringResource(screen.resourceId!!)) },
                        selected = currentTab == screen.route.destination,
                        selectedContentColor = selectedItemColor,
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            events(CommonEvent.ChangeTab(screen))
                            events(CommonEvent.ChangeHasDeepScreen(false, ""))
                            events(CommonEvent.ChangeBackPageData(BackPageData()))
                            navigate(screen.route)

                            if(screen == Screen.Music){

                                musicEvents(MusicEvent.LoadHome)
                            }




                        }
                    )
                }
            }
        }
    }
}