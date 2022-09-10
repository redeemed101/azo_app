package com.fov.main.ui.payment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.payment.PaymentGeneralScreen
import com.fov.main.ui.sermons.audio.screens.AlbumBottomSheetHeader
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState
import com.fov.payment.ui.stripe.StripeForm
import com.fov.payment.viewModels.PaymentViewModel
import com.fov.sermons.events.MusicEvent


@Composable
fun StripeScreen(
    commonViewModel: CommonViewModel,
    paymentViewModel: PaymentViewModel
){

    val commonState by commonViewModel.uiState.collectAsState()
    val payState by paymentViewModel.uiState.collectAsState()
    stripeScreen(commonState = commonState, events = commonViewModel::handleCommonEvent ,
        payState = payState,
        payEvent = paymentViewModel::handlePaymentEvent )
}

@Composable
private fun stripeScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    payState: PayState,
    payEvent: (PayEvent) -> Unit
){
    PaymentGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {}) {
        val backgroundColor = MaterialTheme.colors.surface
        val tintColor = MaterialTheme.colors.onSurface
        LaunchedEffect(commonState.hasDeepScreen) {
            events(CommonEvent.ChangeHasDeepScreen(true,"Debit Card"))
            //events(CommonEvent.ChangeShowMoreOptions(true))
            //events(CommonEvent.ChangeTopBarColor(backgroundColor))
            //events(CommonEvent.ChangeTopBarTintColor(tintColor))
            payEvent(PayEvent.LoadStripeClientSecret)


        }
        DisposableEffect(true){
            onDispose {
                events(CommonEvent.ChangeHasDeepScreen(false, ""))
                //events(CommonEvent.ChangeTopBarColor(backgroundColor))
                events(CommonEvent.ChangeBottomSheetHeader{
                })
            }
        }
        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val verticalGradientBrush = Brush.verticalGradient(
                colors = listOf(
                    backgroundColor,
                    backgroundColor,
                    backgroundColor,
                    backgroundColor
                )
            )
            val scrollState = rememberScrollState(0)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = verticalGradientBrush)
                    .verticalScroll(scrollState)
            ) {
                StripeForm(
                    modifier = Modifier.fillMaxWidth(),
                    payState = payState,
                    payEvents = payEvent)
            }
        }

    }
}