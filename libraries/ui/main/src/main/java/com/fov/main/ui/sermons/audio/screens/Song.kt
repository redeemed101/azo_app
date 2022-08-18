package com.fov.main.ui.sermons.audio.screens


import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.fov.sermons.R
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.White009
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.sections.Section
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.general.SongListItem
import com.fov.domain.database.models.DownloadedSong
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.navigation.Screen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.MusicState
import com.fov.sermons.ui.music.MusicItem
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import com.google.android.exoplayer2.ExoPlayer
import kotlin.text.Typography.section


@ExperimentalAnimationApi
@Composable
fun SongScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel
){

    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val isDownloaded by storedMusicViewModel.isSongDownloadedAsync(musicState.selectedSong!!.songId).collectAsState(
        initial = false
    )
    Song(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
        storedMusicEvents = storedMusicViewModel::handleMusicEvent,
        isDownloaded = isDownloaded
    )


}

@ExperimentalAnimationApi
@Composable
private fun Song(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    storedMusicEvents: (event: StoredMusicEvent) -> Unit,
    isDownloaded : Boolean
) {
    MusicGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            if(musicState.selectedSong != null)
               musicEvents(MusicEvent.SongSelected(musicState.selectedSong!!))
        }
    ) {

        val scope = rememberCoroutineScope()
        val backgroundColor = MaterialTheme.colors.surface
        val  context  = LocalContext.current
        var exoPlayer = musicState.player
        if(musicState.player == null) {
            exoPlayer = remember {
                ExoPlayer.Builder(context).build()
            }
        }

        LaunchedEffect(commonState.hasDeepScreen) {
            events(CommonEvent.ChangeHasDeepScreen(true,""))
            events(CommonEvent.ChangeShowMoreOptions(true))
            events(CommonEvent.ChangeTopBarColor(backgroundColor))
            //events(CommonEvent.ChangeHasTopBar(false))



            if(musicState.selectedSong != null) {
                events(CommonEvent.ChangeBottomSheetHeader{
                    SongBottomSheetHeader(musicState.selectedSong!!)
                })

                //musicEvents(MusicEvent.SongSelected(musicState.selectedSong!!))
            }

        }
        DisposableEffect(true){
            onDispose {
                events(CommonEvent.ChangeHasDeepScreen(false, ""))
                musicEvents(MusicEvent.ChangeShowingSong(false))
                events(CommonEvent.ChangeShowMoreOptions(false))
                events(CommonEvent.ChangeTopBarColor(White009))
                //events(CommonEvent.ChangeHasTopBar(true))
                events(CommonEvent.ChangeBottomSheetHeader{
                })
            }
        }

        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val song = musicState.selectedSong!!

            val isLiked = song.userLikes.contains(commonState.user!!.id)


            val verticalGradientBrush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.surface,
                    MaterialTheme.colors.surface,
                    MaterialTheme.colors.surface,
                    MaterialTheme.colors.surface
                )
            )
            val scrollState = rememberScrollState(0)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = verticalGradientBrush)
                    .verticalScroll(scrollState)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = song.artwork,
                        builder = {
                            crossfade(true)
                            fallback(com.fov.common_ui.R.drawable.image_placeholder)
                            placeholder(com.fov.common_ui.R.drawable.image_placeholder)
                        }
                    ),
                    "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(200.dp)
                )
                Text(
                    song.songName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5.copy(
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(commonPadding)
                ) {
                    IconView(
                         R.drawable.ic_arrow_down_circle,
                        "Download",
                        tint   = if(isDownloaded) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    ) {
                        if(!isDownloaded) {
                            com.fov.sermons.utils.helpers.Utilities.downloadSong(
                                context = context,
                                lifecycleOwner = lifecycleOwner,
                                song = song,
                                changeDownloadData = { downloadUrl, details, destinationFilePath ->
                                    events(
                                        CommonEvent.ChangeDownloadData(
                                            DownloadData(
                                                downloadUrl = downloadUrl,
                                                details = details,
                                                destinationFilePath = destinationFilePath
                                            )
                                        )
                                    )
                                },
                            ) { songPath, imagePath ->
                                storedMusicEvents(
                                    StoredMusicEvent.SaveDownloadedSong(
                                        DownloadedSong(
                                            songName = song.songName,
                                            songPath = songPath,
                                            songId = song.songId,
                                            artistName = song.artistName,
                                            imagePath = imagePath

                                        )
                                    )
                                )
                            }
                        }
                        else{
                            com.fov.sermons.utils.helpers.Utilities.unDownloadSong(
                                "",
                            ){
                                storedMusicEvents(StoredMusicEvent.DeleteDownloadedSong(song.songId))
                            }
                        }
                    }
                    IconView(
                        com.fov.common_ui.R.drawable.ic_heart_white,
                        "Add",
                        tint   = if(isDownloaded || isLiked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    ) {
                        if(isLiked){
                            musicEvents(MusicEvent.LikeSong(song.songId,true))
                        }
                        else{
                            musicEvents(MusicEvent.LikeSong(song.songId,false))
                        }
                    }
                    IconView(
                        com.fov.common_ui.R.drawable.ic_send,
                        "Share"
                    ) {}
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(commonPadding)
                        .fillMaxWidth()

                ) {
                    Button(
                        onClick = {
                            if (musicState.player == null){
                                musicEvents(MusicEvent.LoadPlayer(exoPlayer!!))
                            }
                            musicEvents(MusicEvent.AddToNowPlaying(musicState.selectedSong!!))
                            //musicEvents(MusicEvent.PlaySong(musicState.selectedSong!!))
                            //added
                            musicEvents(MusicEvent.NavigateToHome)
                            events(CommonEvent.ChangeHasTopBar(false))
                            events(CommonEvent.ChangeTab(Screen.Music))

                        },
                        shape = RoundedCornerShape(10),
                        modifier = Modifier.width(screenWidth * 0.4f)
                    ) {


                        Row {
                            Icon(
                                painter = painterResource(com.fov.common_ui.R.drawable.ic_play_circle),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {

                                    },
                                tint = MaterialTheme.colors.onSurface,
                                contentDescription = ""
                            )
                            Text("Play", color = Color.White)
                        }
                    }
                    Button(
                        onClick = {

                        },
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        modifier = Modifier
                            .width(screenWidth * 0.4f)
                            .border(width = 1.dp, MaterialTheme.colors.primary)
                    ) {


                        Row {
                            Icon(
                                painter = painterResource(com.fov.common_ui.R.drawable.ic_shuffle),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                    },
                                tint = MaterialTheme.colors.primary,
                                contentDescription = ""
                            )
                            Text("Shuffle", color = MaterialTheme.colors.primary)
                        }
                    }
                }

                //info
                SongListItem(songNumber = 1,song = musicState.selectedSong!!, isDownloadedSong = isDownloaded) {
                    commonState.bottomSheetAction()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = commonPadding)
                ) {
                    Text(
                        musicState.selectedSong!!.description,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.caption.copy(
                            MaterialTheme.colors.onSurface,

                            ),

                        )
                }
                //More from artist
                val songs = musicState.forYouPaged?.collectAsLazyPagingItems()
                Box(
                    //modifier = Modifier.padding(commonPadding)
                ) {
                    Section(
                        title = "More from ${musicState.selectedSong!!.artistName}",
                        action = {},
                        showSeeAll = true,
                        seeAllAction = {}
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            itemsCustomized(songs!!) { song,_ ->
                                MusicItem(song = song!!) {
                                    musicEvents(MusicEvent.SongSelected(song!!))
                                    musicEvents(MusicEvent.ChangeShowingSong(true))
                                }
                            }
                        }
                    }
                }




                Spacer(modifier = Modifier.height(100.dp))

            }


        }
    }
}
@Composable
fun IconView(
    @DrawableRes icon : Int,
    text : String,
    tint  : Color =  MaterialTheme.colors.onSurface,
    action : () -> Unit,

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(painter = painterResource(icon),
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    action()
                },
            tint = tint,
            contentDescription = ""
        )
        Text(
            text,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption.copy(
                MaterialTheme.colors.onSurface,

                ),

            )

    }
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
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = song.artwork)
                    .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    fallback(com.fov.common_ui.R.drawable.image_placeholder)
                    placeholder(com.fov.common_ui.R.drawable.image_placeholder)
                }).build()
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