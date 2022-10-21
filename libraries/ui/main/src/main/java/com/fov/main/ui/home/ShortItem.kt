package com.fov.main.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fov.domain.models.shorts.Short
import com.fov.main.ui.home.ShortCircle

@Composable
fun ShortItem(short: Short, onClick : () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShortCircle(
            short,
            100.dp,
            10.dp,
            true
        ) {
            onClick()
        }
        Text(
            short.name,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.surface,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
        )
    }
}