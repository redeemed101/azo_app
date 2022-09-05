package com.fov.payment.ui.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.theme.commonPadding
import com.fov.payment.R

import com.fov.payment.models.PaymentMethod

@Preview(showBackground = true)
@Composable
fun prevPaymentMethodItem(

){
    AzoTheme {
        PaymentMethodItem(
            method = PaymentMethod("Debit Card", "Pay through your card", R.drawable.credit_card),
            modifier = Modifier
                .fillMaxWidth()
                .padding(commonPadding)
        )
    }
}

@Composable
fun PaymentMethodItem(
    method : PaymentMethod,
    modifier : Modifier,
    tintColor : Color = MaterialTheme.colors.primary,
    onClick : () -> Unit = {}
){
    Row(
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Start,
       modifier = modifier

    ){
        Icon(
            painterResource(method.icon),
            "",
            tint = tintColor,
            modifier = Modifier
                //.padding(top = 5.dp, bottom = 5.dp)
                //.height(50.dp)
                //.width(screenWidth * 0.2f)
                .clickable {

                }

        )
        Column(
            modifier = Modifier
            .padding(start = 10.dp)
        ) {
            Text(
                method.name,
                modifier = Modifier.padding(top = 2.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h5.copy(
                    MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp

                ),
            )
            Text(
                method.description,
                modifier = Modifier.padding(top = 2.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.surface,
                    fontSize = 10.sp

                ),
            )
        }
    }
}
