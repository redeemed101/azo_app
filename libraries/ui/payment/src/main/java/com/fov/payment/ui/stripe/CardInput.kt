package com.fov.payment.ui.stripe

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.fov.common_ui.theme.AzoTheme
import com.fov.payment.events.PayEvent
import com.stripe.android.view.CardInputWidget



@Composable
fun CardInput(
    modifier: Modifier,
    payEvents: (PayEvent) -> Unit
){
    var input : CardInputWidget? = null;
    AndroidView(
        modifier = modifier,
        factory = { context ->
            //val contextThemeWrapper = ContextThemeWrapper(context,R.style.Theme_Azo_NoActionBar)
            input = CardInputWidget(context).apply {

                postalCodeEnabled = false
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            }
            payEvents(PayEvent.LoadStripeWidget(input!!))
            input!!
        }
    )

}