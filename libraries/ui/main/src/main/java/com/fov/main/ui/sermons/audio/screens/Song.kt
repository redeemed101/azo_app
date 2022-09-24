package com.fov.main.ui.sermons.audio.screens


import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asFlow
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.fov.sermons.R
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.fov.authentication.states.UsersState
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.extensions.itemsCustomized
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.White009
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.sections.Section
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.core.utils.ConnectionManager
import com.fov.main.ui.sermons.audio.general.SongListItem
import com.fov.domain.database.models.DownloadedSong
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.navigation.Screen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Song
import com.fov.sermons.states.MusicState
import com.fov.sermons.states.StoredMusicState
import com.fov.sermons.ui.music.MusicItem
import com.fov.sermons.utils.helpers.Utilities
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel
import com.google.android.exoplayer2.ExoPlayer
import kotlin.text.Typography.section


@ExperimentalAnimationApi
@Composable
fun SongScreen(
    musicViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
    storedMusicViewModel: StoredSermonViewModel,
){

    val commonState by commonViewModel.uiState.collectAsState()
    val musicState by musicViewModel.uiState.collectAsState()
    val storedMusicState by storedMusicViewModel.uiState.collectAsState()
    val isDownloaded by storedMusicViewModel.isSongDownloadedAsync(musicState.selectedSong!!.songId)
        .collectAsState(initial = false)
    val downloadingState by storedMusicViewModel.downloadStateInfo.observeAsState( initial = listOf())
    val songPath by storedMusicViewModel.getSongPath(musicState.selectedSong!!.songId)
        .collectAsState(initial = "")
    Song(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = musicState,
        musicEvents = musicViewModel::handleMusicEvent,
        storedMusicState = storedMusicState,
        storedMusicEvents = storedMusicViewModel::handleMusicEvent,
        isDownloaded = isDownloaded,
        songPath,
        downloadingState
    )


}

@ExperimentalAnimationApi
@Composable
private fun Song(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    storedMusicState: StoredMusicState,
    storedMusicEvents: (event: StoredMusicEvent) -> Unit,
    isDownloaded : Boolean,
    songPath : String,
    downloadingState: List<Pair<String, Float?>>?
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
        val tintColor = MaterialTheme.colors.onSurface
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
            events(CommonEvent.ChangeTopBarTintColor(tintColor))
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
                events(CommonEvent.ChangeBottomSheetHeader{
                })
            }
        }

        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val lifecycleOwner = LocalLifecycleOwner.current
            val song = musicState.selectedSong!!

            val isLiked = false//song.userLikes.contains(commonState.user!!.id)


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
                    var downloadIcon = R.drawable.ic_arrow_down_circle
                    var downloadText = "Download"
                    if(isDownloaded) {
                        downloadIcon = R.drawable.ic_downloaded
                        downloadText = "Un-download"
                    }
                    Row {
                        var songProgressData = downloadingState!!.firstOrNull { p -> p.first == song.songId }

                        if( songProgressData != null && !isDownloaded) {
                            if(songProgressData.second != null)
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(
                                        progress = songProgressData.second!!,
                                        modifier = Modifier.size(24.dp).border(width = 2.dp,color = Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(50)),
                                        color = MaterialTheme.colors.onSurface
                                    )
                                    Text(text = "Download",color = MaterialTheme.colors.onSurface)
                                }
                            //ShimmerAnimation(size = 24.dp, isCircle = true)
                        }
                        else
                            //ShimmerAnimation(size = 24.dp, isCircle = true)
                        {

                            IconView(
                                downloadIcon,
                                downloadText,
                                tint = MaterialTheme.colors.onSurface
                            ) {
                                if (!isDownloaded) {
                                    val isInternet = ConnectionManager.isInternetAvailable(context)
                                    if(isInternet){
                                        storedMusicEvents(StoredMusicEvent.DownloadSong(
                                            song,
                                            commonState.user!!.privateKey
                                        ))
                                    }
                                } else {
                                    Utilities.unDownloadSong(
                                        songPath,
                                    ) {
                                        storedMusicEvents(StoredMusicEvent.DeleteDownloadedSong(song.songId))
                                    }
                                }
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
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onSurface
                        ),
                        onClick = {
                            if (musicState.player == null){
                                musicEvents(MusicEvent.LoadPlayer(exoPlayer!!))
                            }
                            //musicEvents(MusicEvent.ChangeSongSelected(song!!))
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
                                tint = MaterialTheme.colors.surface,
                                contentDescription = ""
                            )
                            Text("Play", color = MaterialTheme.colors.surface)
                        }
                    }
                    Button(
                        onClick = {

                        },
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface
                        ),
                        modifier = Modifier
                            .width(screenWidth * 0.4f)
                            .border(width = 1.dp, MaterialTheme.colors.onSurface)
                    ) {


                        Row {
                            Icon(
                                painter = painterResource(com.fov.common_ui.R.drawable.ic_shuffle),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                    },
                                tint = MaterialTheme.colors.onSurface,
                                contentDescription = ""
                            )
                            Text("Shuffle", color = MaterialTheme.colors.onSurface)
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

                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))

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
                    ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = song.artwork)
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
                    MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                song.artistName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.surface,

                    ),

                )
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
}

fun downloadSong(context : Context,
                 lifecycleOwner: LifecycleOwner,
                 privateKey: String,
                 song: Song,
                 events: (event: CommonEvent) -> Unit,
                 storedMusicEvents : (event : StoredMusicEvent) -> Unit
){
    Utilities.downloadSong(
        context = context,
        encryptionKey = privateKey,
        song = song,
        changeDownloadData = { downloadUrl,
                               details,
                               destinationFilePath ->
            events(
                CommonEvent.ChangeDownloadData(
                    DownloadData(
                        downloadUrl = downloadUrl,
                        details = details,
                        destinationFilePath = destinationFilePath
                    )
                )
            )
        }
    ).observe(lifecycleOwner) { workInfo ->
        if (workInfo.state.isFinished) {
            val data = workInfo.outputData
            val dataMap = data.keyValueMap
            if (dataMap.containsKey("FILEPATH")) {
                val arrPaths = dataMap["FILEPATH"] as Array<String>
                //save to downloadedSongsDatabase
                storedMusicEvents(
                    StoredMusicEvent.SaveDownloadedSong(
                        DownloadedSong(
                            songName = song.songName,
                            songPath = arrPaths[0],
                            songId = song.songId,
                            artistName = song.artistName,
                            imagePath = arrPaths[1]

                        )
                    )
                )
                storedMusicEvents(
                    StoredMusicEvent.UpdateSongDownloadProgress(
                        null, song.songId
                    )
                )
                Log.d("PROGRESS", "DONE")

            } else {
                //show error
            }

        } else {
            val progress = workInfo.progress
            val value = progress.getInt("progress", 1)
            Log.d("PROGRESS", (value / 100.00).toFloat().toString())
            Log.d("PROGRESS_TEST", (2 / 100.00).toFloat().toString())
            storedMusicEvents(
                StoredMusicEvent.UpdateSongDownloadProgress(
                    (value / 100.00).toFloat(), song.songId
                )
            )
        }
    }


}