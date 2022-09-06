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



@Composable
fun PaymentMethodItem(
    method : PaymentMethod,
    modifier : Modifier,
    tintColor : Color = MaterialTheme.colors.surface,
    onClick : () -> Unit = {}
){
    Row(
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Start,
       modifier = modifier.clickable {
           onClick()
       }

    ){
        Icon(
            painterResource(method.icon),
            "",
            tint = tintColor,
            modifier = Modifier.size(60.dp)


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
