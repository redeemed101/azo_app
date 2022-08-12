package com.fidarr.music.ui.general.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.FidarrAppComposeTheme
import com.fidarr.music.ui.EmphasisText
import com.fidarr.music.ui.IconButton


@Preview
@Composable
fun prevMusicPlayerBar(){
    FidarrAppComposeTheme {
        MusicPlayerBar()
    }
}

@Composable
fun MusicPlayerBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 1.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            imageVector = Icons.Filled.KeyboardArrowDown,
            size = 35.dp,
            onClick = {/*TODO*/ }
        )
        EmphasisText(text = "NOW PLAYING")
        IconButton(
            imageVector = Icons.Filled.MoreVert,
            size = 35.dp,
            onClick = { /*TODO*/ }
        )
    }
}