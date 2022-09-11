package com.fov.main.ui.sermons.audio.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R
import com.fov.common_ui.events.CommonEvent
import com.fov.common_ui.models.DownloadData
import com.fov.common_ui.states.CommonState
import com.fov.common_ui.theme.bottomTabHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.viewModels.CommonViewModel
import com.fov.main.ui.sermons.audio.general.SongListItem
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.main.ui.sermons.audio.general.MusicGeneralScreen
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.events.StoredMusicEvent
import com.fov.sermons.models.Album
import com.fov.sermons.states.MusicState
import com.fov.sermons.viewModels.SermonViewModel
import com.fov.sermons.viewModels.StoredSermonViewModel


@ExperimentalAnimationApi
@Composable
fun AlbumScreen(
    sermonsViewModel : SermonViewModel,
    commonViewModel: CommonViewModel,
    storedSermonViewModel: StoredSermonViewModel,
){
    val commonState by commonViewModel.uiState.collectAsState()
    val sermonsState by sermonsViewModel.uiState.collectAsState()

    val isDownloaded by storedSermonViewModel.isAlbumDownloadedAsync(sermonsState.selectedAlbum!!.albumId).collectAsState(
        initial = false
    )
    val albumPath by storedSermonViewModel.getAlbumPath(sermonsState.selectedAlbum!!.albumId)
        .collectAsState(initial = "")
    AlbumView(
        commonState = commonState,
        events = commonViewModel::handleCommonEvent,
        musicState = sermonsState,
        musicEvents = sermonsViewModel::handleMusicEvent,
        storedMusicEvents = storedSermonViewModel::handleMusicEvent,
        isDownloaded = isDownloaded,
        albumPath

    )
}

@ExperimentalAnimationApi
@Composable
private fun AlbumView(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    musicState: MusicState,
    musicEvents: (event: MusicEvent) -> Unit,
    storedMusicEvents: (event: StoredMusicEvent) -> Unit,
    isDownloaded :  Boolean,
    albumPath : String
){
    MusicGeneralScreen(
        commonState = commonState,
        events = events,
        musicState = musicState,
        musicEvents = musicEvents,
        swipeToRefreshAction = {
            if(musicState.selectedAlbum != null)
                musicEvents(MusicEvent.AlbumSelected(musicState.selectedAlbum!!))
        }
    ) {
        val backgroundColor = MaterialTheme.colors.surface
        val tintColor = MaterialTheme.colors.onSurface

        LaunchedEffect(commonState.hasDeepScreen) {
            events(CommonEvent.ChangeHasDeepScreen(true,""))
            events(CommonEvent.ChangeShowMoreOptions(true))
            events(CommonEvent.ChangeTopBarColor(backgroundColor))
            events(CommonEvent.ChangeTopBarTintColor(tintColor))

            if(musicState.selectedAlbum != null) {
                events(CommonEvent.ChangeBottomSheetHeader{
                    AlbumBottomSheetHeader(musicState.selectedAlbum!!)
                })
                //sermonsEvents(sermonsEvent.AlbumSelected(sermonsState.selectedAlbum!!))
            }

        }
        DisposableEffect(true){
            onDispose {
                events(CommonEvent.ChangeHasDeepScreen(false, ""))
                musicEvents(MusicEvent.ChangeShowingAlbum(false))
                events(CommonEvent.ChangeTopBarColor(backgroundColor))
                events(CommonEvent.ChangeBottomSheetHeader{
                })
            }
        }
        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val album = musicState.selectedAlbum!!
            val isLiked = false//musicState.selectedAlbum!!.userLikes.contains(commonState.user!!.id)
            val verticalGradientBrush = Brush.verticalGradient(
                colors = listOf(
                    backgroundColor,
                    backgroundColor,
                    backgroundColor,
                    backgroundColor
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
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = musicState.selectedAlbum!!.artwork)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                fallback(R.drawable.image_placeholder)
                                placeholder(R.drawable.image_placeholder)
                            }).build()
                    ),
                    "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(200.dp)
                )
                Text(
                    musicState.selectedAlbum!!.albumName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5.copy(
                        MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    musicState.selectedAlbum!!.artistName,
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
                    var downloadIcon = com.fov.sermons.R.drawable.ic_arrow_down_circle
                    var downloadText = "Download"
                    var downloadTint = MaterialTheme.colors.onSurface
                    if(isDownloaded) {
                        downloadIcon = com.fov.sermons.R.drawable.ic_downloaded
                        downloadText = "Un-download"
                    }
                    IconView(
                        downloadIcon,
                        downloadText,
                        tint   =  downloadTint
                    ) {
                        if(!isDownloaded){
                            com.fov.sermons.utils.helpers.Utilities.downloadAlbum(
                                context = context,
                                lifecycleOwner = lifecycleOwner,
                                album = album,
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
                                saveSong = { song, songPath, imagePath ->
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
                            ){ albumPath, imagePath ->
                                storedMusicEvents(StoredMusicEvent.SaveDownloadedAlbum(
                                    album = DownloadedAlbum(
                                        albumName = album.albumName,
                                        albumPath = albumPath,
                                        albumId = album.albumId,
                                        artistName = album.artistName,
                                        imagePath = imagePath,
                                    )
                                  )
                                )
                            }
                        }
                        else{
                            com.fov.sermons.utils.helpers.Utilities.unDownloadAlbum(
                                albumPath,
                            ){
                                storedMusicEvents(StoredMusicEvent.DeleteDownloadedAlbum(albumId = album.albumId))
                            }
                        }
                    }
                    var likedIcon  = com.fov.common_ui.R.drawable.ic_heart_white
                    var likedBackground  = MaterialTheme.colors.onSurface
                    if(isDownloaded || isLiked) {
                        likedIcon = com.fov.common_ui.R.drawable.ic_heart_filled
                        likedBackground = MaterialTheme.colors.primary
                    }
                    IconView(
                        likedIcon,
                        "Add",
                        tint   = likedBackground
                    ) {
                        if(isLiked){
                            musicEvents(MusicEvent.LikeAlbum(album.albumId,true))
                        }
                        else{
                            musicEvents(MusicEvent.LikeAlbum(album.albumId,false))
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
                            .border(width = 1.dp, MaterialTheme.colors.primary)
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
                //songs
                musicState.selectedAlbum!!.songs.forEachIndexed { index, song ->

                    SongListItem(songNumber = index + 1, song =song ) {
                        commonState.bottomSheetAction()
                    }

                }
                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))
            }
        }
    }
}

@Composable
 fun AlbumBottomSheetHeader(
    album : Album
){
    Row(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = album.artwork).apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    fallback(R.drawable.image_placeholder)
                    placeholder(R.drawable.image_placeholder)
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
                album.albumName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                album.artistName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.surface,

                    ),

                )
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
}