package com.fov.common_ui.ui.composers.headers

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.R


@Composable
fun mainNewsBar(
    backgroundColor: Color = MaterialTheme.colors.onSurface,
    tintColor : Color =   MaterialTheme.colors.surface,
    size : Dp = 60.dp,
    searchClicked : () -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .background(backgroundColor)
    ){
        Text(
            "News",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = commonPadding),
            style = MaterialTheme.typography.h5.copy(
                tintColor,
                fontWeight = FontWeight.Bold,
            ),
        )
        /*Icon(
            painterResource(R.drawable.ic_news),
            "",
            tint = tintColor,
            modifier = Modifier
                .padding(horizontal = commonPadding)
                .height(size * 0.3f)
                .width(size * 0.3f)
                .clickable {
                    searchClicked()
                }

        )*/
    }
}