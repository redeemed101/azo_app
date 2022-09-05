package com.fov.payment.ui.stripe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.commonPadding
import com.fov.payment.models.PaymentMethod
import com.fov.payment.ui.general.PaymentMethodItem

@Composable
fun ChoosePaymentOption(
    methods : List<PaymentMethod>,
){
    BoxWithConstraints() {
        val screenWidth = maxWidth
        Column {
            methods.forEach { method ->
                PaymentMethodItem(
                    method = method,
                    modifier = Modifier
                        .fillMaxWidth().padding(commonPadding)
                        .clip(RoundedCornerShape(10))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onSurface,
                            shape = RoundedCornerShape(10)
                        )
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.8f))
                        .padding(commonPadding),

                ){

                }
            }
        }
    }
}