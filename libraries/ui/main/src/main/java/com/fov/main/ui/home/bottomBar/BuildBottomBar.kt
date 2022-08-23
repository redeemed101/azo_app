package com.fov.main.ui.home.bottomBar

import android.content.Intent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    backgroundColor: Color = MaterialTheme.colors.onSurface,
    selectedItemColor : Color =   MaterialTheme.colors.surface,
    unSelectedItemColor : Color =   MaterialTheme.colors.primary,
    commonState: CommonState,
    events : (CommonEvent) -> Unit,
    musicEvents :  (MusicEvent) -> Unit,
    navigate : (route : NavigationCommand) -> Unit,
    currentTab : String,

) : @Composable () -> Unit{
    val context = LocalContext.current
    return {

        if(commonState.hasBottomBar){

            BottomNavigation(
                modifier = Modifier.height(80.dp).zIndex(1f),
                backgroundColor = backgroundColor,
                contentColor = DarkGrey,
                elevation = 100.dp
            ) {


                tabItems.forEach { screen ->
                    val size = if(currentTab == screen.route.destination) 60.dp else 30.dp
                    BottomNavigationItem(
                        modifier = Modifier.zIndex(2f),
                        icon = {

                                Icon(
                                    painter = painterResource(screen.iconResourceId!!),
                                    "",
                                    modifier = Modifier.size(size),

                                )

                        },
                        label =  {
                                   //if(currentTab != screen.route.destination)
                                      Text(
                                          stringResource(screen.resourceId!!)
                                      )


                                 } ,
                        selected = currentTab == screen.route.destination,
                        selectedContentColor = selectedItemColor,
                        unselectedContentColor = unSelectedItemColor,
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