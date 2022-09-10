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
import com.fov.payment.R
import com.fov.payment.events.PayEvent
import com.fov.payment.models.PaymentMethod
import com.fov.payment.states.PayState
import com.fov.payment.ui.stripe.ChoosePaymentOption
import com.fov.payment.ui.stripe.StripeForm
import com.fov.payment.viewModels.PaymentViewModel

@Composable
fun PaymentOptionsScreen(
    commonViewModel: CommonViewModel,
    paymentViewModel: PaymentViewModel
){

    val commonState by commonViewModel.uiState.collectAsState()
    val payState by paymentViewModel.uiState.collectAsState()
    paymentOptionsScreen(commonState = commonState, events = commonViewModel::handleCommonEvent ,
        payState = payState,
        payEvents = paymentViewModel::handlePaymentEvent )
}
@Composable
private fun  paymentOptionsScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    payState: PayState,
    payEvents: (PayEvent) -> Unit
){
    PaymentGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {}) {
        val backgroundColor = MaterialTheme.colors.surface
        val tintColor = MaterialTheme.colors.onSurface
        LaunchedEffect(commonState.hasDeepScreen) {
            //events(CommonEvent.ChangeHasDeepScreen(true, "Payment Options"))
            payEvents(PayEvent.LoadStripeClientSecret)

        }
        DisposableEffect(true){
            onDispose {

                events(CommonEvent.ChangeBottomSheetHeader{
                })
            }
        }
        BoxWithConstraints {

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
                ChoosePaymentOption(payState, payEvents = payEvents)
            }
        }

    }
}