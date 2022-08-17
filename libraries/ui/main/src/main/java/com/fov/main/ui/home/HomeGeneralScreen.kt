package com.fov.main.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeGeneralScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    swipeToRefreshAction : () -> Unit = {},
    backHandler : () -> Unit = {},
    pageContent : @Composable BoxScope.() -> Unit,
){

    BackHandler(onBack = {
        backHandler()
    })
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {


        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(commonState.toast) {
            lifecycleOwner.lifecycleScope.launch {

                if (commonState.toast != null) {
                    Toast.makeText(context, commonState.toast, Toast.LENGTH_SHORT).show()
                }


            }
        }
        Box{
            SwipeRefresh(
                state = rememberSwipeRefreshState(commonState.isRefreshing),
                onRefresh = {
                   swipeToRefreshAction()
                },
            ) {
                if (commonState.loading) {
                    LoadingBox()
                }
                else {
                    pageContent()
                }
            }
            Box (
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ){

            }
        }

    }

}