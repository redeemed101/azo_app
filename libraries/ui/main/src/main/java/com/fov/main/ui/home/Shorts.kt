package com.fov.main.ui.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.main.ShortsActivity
import com.fov.shorts.events.ShortEvent
import com.fov.shorts.states.ShortState


@Composable
fun Shorts(
    shortState : ShortState,
    events: (event: ShortEvent) -> Unit,
    title : String = ""
){

    shorts(
        shortState,
        events,
        title

    )
}

@Composable
private fun shorts(

    shortState : ShortState,
    events: (event: ShortEvent) -> Unit,
    title : String,
){
    val context = LocalContext.current

    val lazyShorts = shortState.shorts?.collectAsLazyPagingItems()

    var backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.9f);

    Column(
        modifier = Modifier.background(backgroundColor)
    ) {

        Text(

            title,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.h2.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp

            )
        )

        Row(

            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            LazyRow(modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                itemsCustomized(lazyShorts!!){ short,idx ->
                    ShortItem(short = short!!) {
                        events(ShortEvent.ShortSelected(short!!))
                        var intent = Intent(context, ShortsActivity::class.java)
                        intent.putExtra("shortType",short!!.type)
                        intent.putExtra("shortPath",short!!.path)
                        context.startActivity(intent)
                        events(ShortEvent.ToggleShowShort(true))

                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}