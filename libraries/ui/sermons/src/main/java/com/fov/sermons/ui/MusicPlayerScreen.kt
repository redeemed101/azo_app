package com.fov.sermons.ui


import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.rememberImagePainter
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.utils.helpers.ShimmerAnimation
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.sermons.R
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.music.MusicPlayer
import com.fov.sermons.viewModels.SermonViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.math.abs
import kotlin.math.roundToInt

@InternalCoroutinesApi
@Composable
fun MusicPlayerScreen(
    sermonViewModel: SermonViewModel,
    commonViewModel: CommonViewModel
){
    val state by sermonViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    MusicPlayerView(
        commonState = commonState,
        commonEvents = commonViewModel::handleCommonEvent,
        musicState = state,
        musicEvents = sermonViewModel::handleMusicEvent
    )
}
// variable darkTheme is used just for the sake of testing both light and dark mode
@Composable
private fun MusicPlayerView(
    commonState: CommonState,
    commonEvents : (event: CommonEvent) -> Unit,
    musicState : MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
) {
    ProvideWindowInsets {
        BackHandler(onBack = {
            commonEvents(CommonEvent.ChangeHasDeepScreen(false,""))
            commonEvents(CommonEvent.NavigateUp)
        })
        val isPlaying =
            remember { mutableStateOf(musicState.isSongStarted) }
        var isSongLoaded by
            remember { mutableStateOf(true) }
        val  context  = LocalContext.current
        val backgroundColor = MaterialTheme.colors.surface

        Surface(
            color = MaterialTheme.colors.surface, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            LaunchedEffect(commonState.hasDeepScreen) {
                commonEvents(CommonEvent.ChangeHasDeepScreen(true, ""))
                commonEvents(CommonEvent.ChangeHasBottomBar(false))
                commonEvents(CommonEvent.ChangeTopBarColor(backgroundColor))
                commonEvents(CommonEvent.ChangeShowMoreOptions(true))
                if(musicState.currentSong != null) {
                    commonEvents(CommonEvent.ChangeBottomSheetHeader{
                        SongBottomSheetHeader(musicState.currentSong!!)
                    })

                    //musicEvents(MusicEvent.SongSelected(musicState.selectedSong!!))
                }
            }
            DisposableEffect(true){
                onDispose {
                    commonEvents(CommonEvent.ChangeHasDeepScreen(false, ""))
                    commonEvents(CommonEvent.ChangeHasTopBar(true))
                    commonEvents(CommonEvent.ChangeHasBottomBar(true))
                    commonEvents(CommonEvent.ChangeShowMoreOptions(false))

                }
            }
            BoxWithConstraints() {
                val maxHeight = maxHeight
                val maxWidth = maxWidth

                val scrollState = rememberScrollState(0)

                 Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .fillMaxSize()
                        ) {




                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                EmphasisText(
                                    text = musicState.currentSong?.songName ?: "Lewis",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(44.dp))
                                Image(
                                    painter = rememberImagePainter(
                                        data = musicState.currentSong?.artwork ?: "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                                        builder = {
                                            crossfade(true)
                                            fallback(ThemeHelper.getLogoResource())
                                            placeholder(ThemeHelper.getLogoResource())
                                        }
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    contentScale = ContentScale.Crop,

                                    )

                                Spacer(modifier = Modifier.height(24.dp))
                                EmphasisText(
                                    text = musicState.currentSong?.songName ?: "Surrender  Anthem",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                var ft = if(musicState.currentSong?.featuredArtists != null && musicState.currentSong?.featuredArtists.isNotEmpty()) "ft ${musicState.currentSong?.featuredArtists?.reduce { acc, string -> "$acc,$string" } ?: ""}" else ""
                                EmphasisText(
                                    text = "${musicState.currentSong?.artistName ?: "Lewis Msasa"} ${ft}",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 18.sp
                                )
                                val currentSource = musicState.currentSong?.path ?: "https://file-fovs-com.github.io/uploads/2017/11/file_fov_MP3_700KB.mp3"
                                if(isSongLoaded) {
                                    MusicPlayer(
                                        modifier = Modifier
                                            .height(maxHeight / 5f)
                                            .width(maxWidth),
                                        onMediaReady = {
                                            isSongLoaded  = true
                                        },
                                        onMediaBuffering = {
                                            isSongLoaded  =  false
                                        },
                                        onMediaError = {
                                            isSongLoaded  = false
                                            Toast.makeText(context,"Loading song failed",Toast.LENGTH_LONG)
                                        },
                                        sources = listOf(currentSource)
                                    )
                                }
                                else{
                                    ShimmerAnimation(size = 20.dp, isCircle = false,showSmallSpacer = false)
                                }


                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp)
                                ) {
                                    IconButton(
                                        painter = painterResource(id = R.drawable.ic_bx_bxs_playlist),
                                        size = 35.dp,
                                        onClick = {/*TODO*/ }
                                    )
                                    IconButton(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        size = 35.dp,
                                        onClick = {/*TODO*/ }
                                    )
                                }
                            }
                        }
        }
    }
}

}


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String? = null,
    size: Dp,
    tint: Color = MaterialTheme.colors.onBackground,
    onClick: () -> Unit
) {
    if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = modifier
                .clickable(onClick = onClick)
                .size(size)
        )
    } else if (painter != null) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier
                .clickable(onClick = onClick)
                .size(size)
        )
    }
}

@Composable
fun EmphasisText(
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1,
        fontWeight = fontWeight,
        maxLines = 1,
        fontSize = fontSize,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun DurationText(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.caption
) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = textStyle
    )
}
@Composable
fun SongBottomSheetHeader(
    song : Song
){
    Row(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = rememberImagePainter(
                data = song.artwork,
                builder = {
                    crossfade(true)
                    fallback(R.drawable.image_placeholder)
                    placeholder(R.drawable.image_placeholder)
                },

                ),
            "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(80.dp)
        )
        Column{
            Text(
                song.songName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                song.artistName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.onSurface,

                    ),

                )
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
}




@Composable
fun MusicPlayerSheet(
    commonState: CommonState,
    commonEvents : (event: CommonEvent) -> Unit,
    musicState : MusicState,
    musicEvents : (event: MusicEvent) -> Unit,
    musicPlayer :  @Composable() () -> Unit = {
        Text(
            "Lewis Msasa Jnr",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption.copy(
                MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                fontSize = 12.sp

            ),
        )
    }
) {
    var offsetY by remember { mutableStateOf(0f) }



        val backgroundColor = MaterialTheme.colors.surface


        BoxWithConstraints() {
            val maxHeight = maxHeight
            val maxWidth = maxWidth

            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 50.dp)
                    .background(MaterialTheme.colors.surface)
                    .offset {

                            IntOffset(0, offsetY.roundToInt())
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if((maxHeight/4f).toPx() >= abs(offsetY)){
                                    offsetY = 0f
                                }
                                else{
                                    musicEvents(MusicEvent.MinimizeMusicPlayer(true))
                                    commonEvents(CommonEvent.ChangeHasTopBar(true))
                                    commonEvents(CommonEvent.ChangeHasBottomBar(true))
                                }
                            }
                        ) { change, dragAmount ->
                            change.consumeAllChanges()
                            if((maxHeight/1.5f).toPx() < abs(offsetY + dragAmount.y)){

                                musicEvents(MusicEvent.MinimizeMusicPlayer(true))
                                commonEvents(CommonEvent.ChangeHasTopBar(true))
                                commonEvents(CommonEvent.ChangeHasBottomBar(true))
                            }
                            else {
                                offsetY += dragAmount.y
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(commonPadding),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painterResource(R.drawable.ic_arrow_down),
                        "",
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp,
                            )
                            .clickable {
                                musicEvents(MusicEvent.MinimizeMusicPlayer(true))
                                commonEvents(CommonEvent.ChangeHasTopBar(true))
                                commonEvents(CommonEvent.ChangeHasBottomBar(true))
                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                    )
                    Text(
                        "NOW PLAYING",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption.copy(
                            MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                            fontSize = 14.sp

                        ),
                    )
                    Image(
                        painterResource(com.fov.common_ui.R.drawable.ic_more_vertical),
                        "",
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp,
                            )
                            .clickable {

                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                    )
                }
                EmphasisText(
                    text = musicState.currentSong?.songName ?: "",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(44.dp))
                Image(
                    painter = rememberImagePainter(
                        data = musicState.currentSong?.artwork
                            ?: "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                        builder = {
                            crossfade(true)
                            fallback(ThemeHelper.getLogoResource())
                            placeholder(ThemeHelper.getLogoResource())
                        }
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,

                    )

                Spacer(modifier = Modifier.height(24.dp))
                EmphasisText(
                    text = musicState.currentSong?.songName ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                var ft =
                    if (musicState.currentSong?.featuredArtists != null && musicState.currentSong?.featuredArtists.isNotEmpty()) "ft ${musicState.currentSong?.featuredArtists?.reduce { acc, string -> "$acc,$string" } ?: ""}" else ""
                EmphasisText(
                    text = "${musicState.currentSong?.artistName ?: ""} ${ft}",
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )


                musicPlayer()


                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    IconButton(
                        painter = painterResource(id = R.drawable.ic_bx_bxs_playlist),
                        size = 35.dp,
                        onClick = {/*TODO*/ }
                    )
                    IconButton(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        size = 35.dp,
                        onClick = {/*TODO*/ }
                    )
                }
            }


        }



}





