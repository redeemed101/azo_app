package com.fov.sermons.ui.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.fov.common_ui.theme.AzoTheme

@Preview(showBackground = true)
@Composable
fun prevSongColumn(){
    AzoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()

        ) {
            songColumn("Top Songs")
        }
    }
}

@Composable
fun songColumn(title : String){


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
            ),
        )
        for(i in 1..4) {
            songRow(
                "$i",
                "Savage Remix (Feat Beyonce)",
                "Enyo Sam"
            )
        }

    }
}