package com.fov.sermons.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.fov.common_ui.utils.constants.AlbumRequestType
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.utils.constants.SongRequestType
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.common_ui.utils.helpers.Utilities
import com.fov.core.di.Preferences
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.domain.database.models.ActivityType
import com.fov.domain.database.models.RecentActivity
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.interactors.news.NewsInteractor
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.domain.models.general.ImagePager
import com.fov.navigation.NavigationManager
import com.fov.navigation.SermonsDirections
import com.fov.sermons.events.MusicEvent
import com.fov.sermons.mock.data.songs.PAGER_IMAGES
import com.fov.sermons.mock.data.videos.VIDEOS
import com.fov.sermons.models.*
import com.fov.sermons.pagination.AlbumsSource
import com.fov.sermons.pagination.SongsSource
import com.fov.sermons.pagination.VideoSource
import com.fov.sermons.states.MusicState
import com.fov.sermons.viewModels.helpers.MusicAlbumHelper
import com.fov.sermons.viewModels.helpers.MusicSongHelper
import com.fov.sermons.viewModels.helpers.RecentActivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SermonViewModel @Inject constructor(
    private val musicInteractor: MusicInteractor,
    private val newsInteractor: NewsInteractor,
    private val videoInteractor : VideoInteractor,
    private val navigationManager: NavigationManager,
    private val fileEncryption : FileEncryption,
    private val sharedPreferences: Preferences,
    application: Application
)  : AndroidViewModel(application) {
    val videoPager = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
        VideoSource(
            videoInteractor = videoInteractor,
            accessToken = accessToken!!
        )
    }.flow.cachedIn(viewModelScope)
    private val context = getApplication<Application>().applicationContext
    var basePath = "${
        com.fov.common_ui.utils.helpers.Utilities
            .getCacheDirectory(
                context
            ).absolutePath}"
    private val _uiState = MutableStateFlow(MusicState())
    val uiState: StateFlow<MusicState> = _uiState

    private var musicAlbumHelper : MusicAlbumHelper? = null
    private var musicSongHelper : MusicSongHelper? = null
    private val recentActivityHelper = RecentActivityHelper(musicInteractor)
    private var accessToken: String? = null


    init {

       viewModelScope.launch {
           sharedPreferences.accessToken?.let { token ->
               token.collectLatest { it ->
                   if(it != null) {
                       accessToken = it
                       musicAlbumHelper = MusicAlbumHelper(
                           musicInteractor = musicInteractor,
                           accessToken = accessToken!!
                       )
                       musicSongHelper = MusicSongHelper(musicInteractor, accessToken = accessToken!!)
                   }
               }
           }
       }

        getTopAlbums()
        getForYou()
        getTopSongs()
        //getVideos()
        getGenres {  }
    }

    fun isSongLiked(song: Song, userId: String) : Boolean {
        return song.userLikes.contains(userId)
    }

    fun handleMusicEvent(event: MusicEvent) {
        _uiState.value = uiState.value.build {

            when (event) {
                MusicEvent.LoadTopSongs ->{
                    topSongsPaged = musicSongHelper?.getTopSongs(viewModelScope){
                        error = it.message
                    }

                }
                is MusicEvent.GetSong ->{
                    viewModelScope.launch {
                        val song = musicSongHelper?.getSong(event.songId)
                        selectedSong = song
                    }
                }
                is MusicEvent.LikeSong ->{
                    viewModelScope.launch {
                        musicInteractor.likeSong(event.songId)
                    }
                }
                is MusicEvent.LikeAlbum ->{
                    viewModelScope.launch {
                        musicInteractor.likeAlbum(event.albumId)
                    }
                }
                MusicEvent.LoadVideos -> {

                        //videos = getVideos()

                }
                MusicEvent.LoadHome -> {
                    newSongs = musicSongHelper?.getNewSongs(viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    newAlbums = musicAlbumHelper?.getNewAlbums(viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    getImagePagers()
                    //musicPagerImages = PAGER_IMAGES



                }
                MusicEvent.GoToGenres ->{
                    navigationManager.navigate(SermonsDirections.genres)
                }
                MusicEvent.LoadGenres -> {

                    loading = true
                    getGenres{
                        loading = false
                    }



                }
                is MusicEvent.LoadPlayer -> {
                    player = event.player
                }


                is MusicEvent.SearchSongTextChanged -> {
                    searchSongText = event.search
                }

                is MusicEvent.SearchSong ->{
                    loading = true
                    recentSongSearch = musicSongHelper?.searchSongs(event.search,viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    loading = false
                }
                MusicEvent.LoadRecentSearch ->{
                    loading = true
                    recentSongSearch = musicSongHelper?.getRecentSongSearch(viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    loading = false
                }

                MusicEvent.LoadTopAlbums -> {
                    topAlbumsPaged = musicAlbumHelper?.getTopAlbums(viewModelScope){
                        error = it.message
                    }

                }
                MusicEvent.LoadForYou -> {
                    forYouPaged = musicSongHelper?.getForYouSongs(viewModelScope){
                        error = it.message
                    }
                }
                MusicEvent.ClearRecentSongSearch -> {
                    musicSongHelper?.deleteRecentSongSearch(viewModelScope){

                    }
                }
                is MusicEvent.SaveRecentSearch -> {
                    musicSongHelper?.saveRecentSongSearch(event.song,viewModelScope){

                    }
                }
                is MusicEvent.AlbumSelected -> {
                    val album = event.album
                    selectedAlbum = album
                    //add to recent activity
                    recentActivityHelper.saveRecentActivity(
                        RecentActivity(
                            id = album.albumId,
                            title = album.albumName,
                            subTitle = album.artistName,
                            type = ActivityType.ALBUM.type,
                            image = album.artwork
                        ),
                        viewModelScope
                    ){

                    }
                    //navigate
                    navigationManager.navigate(SermonsDirections.album)
                }
                is MusicEvent.ChangeSongSelected -> {
                    selectedSong = event.song
                }
                is MusicEvent.MinimizeMusicPlayer -> {
                    isPlayerMinimized = event.minimized
                }
                is MusicEvent.PlaySong -> {
                    currentSong = event.song
                    navigationManager.navigate(SermonsDirections.playSong)
                }
                is MusicEvent.SongStarted ->{
                    songStarted = event.started
                }
                MusicEvent.NavigateToHome ->{
                    navigationManager.navigate(SermonsDirections.tab)
                }
                is MusicEvent.SetMediaPlayback ->{
                    mediaPlayBack = event.player
                }

                is MusicEvent.AddToNowPlaying -> {
                    val song = event.song
                    selectedSong = song
                    //added

                    isPlayerMinimized = false
                    songStarted = false
                    //end of added
                    var currentList: MutableList<Song> = mutableListOf()
                    //if(nowPlaying.isNotEmpty())
                       // currentList = nowPlaying as MutableList<Song>
                    currentList.add(song)
                    nowPlaying = currentList
                    currentSong = event.song
                }
                is MusicEvent.SongSelected -> {
                    val song = event.song
                    selectedSong = song
                    //add to recent activity
                    if(event.downloaded){
                        val destinationFilePath = "$basePath/${event.song.songName}" +
                                "${FileUtilities.getFileExtension(event.song.path)}"
                        val file = fileEncryption.decryptEncryptedFile(
                            event.song.path,
                            destinationFilePath,
                            event.secretKey
                        )
                        selectedSong!!.path = file.absolutePath
                        Log.d("SONG_PATH", file.absolutePath)

                    }
                    else {
                        recentActivityHelper.saveRecentActivity(
                            RecentActivity(
                                id = song.songId,
                                title = song.songName,
                                subTitle = song.artistName,
                                type = ActivityType.SONG.type,
                                image = song.artwork
                            ),
                            viewModelScope
                        ) {

                        }
                    }
                    //navigate
                    navigationManager.navigate(SermonsDirections.song)

                }
                MusicEvent.GoToGenre -> {
                    //navigate to genre
                    navigationManager.navigate(SermonsDirections.genre)
                }
                MusicEvent.LoadLikedMusic -> {
                    val likedAlbums = musicAlbumHelper?.getUserLikedAlbums("userId",viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    val  likedSongs = musicSongHelper?.getLikedSongs("userId",viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    likedMusicData = LikedMusicData(
                        likedAlbums = likedAlbums,
                        likedSongs = likedSongs
                    )
                }
                MusicEvent.GoToLikedMusic ->{
                    val likedAlbums = musicAlbumHelper?.getUserLikedAlbums("userId",viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    val  likedSongs = musicSongHelper?.getLikedSongs("userId",viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    likedMusicData = LikedMusicData(
                        likedAlbums = likedAlbums,
                        likedSongs = likedSongs
                    )
                    //navigationManager.navigate(CollectionDirections.likedMusic)
                }
                is MusicEvent.GenreSelected -> {
                    loading = true
                    selectedGenre = event.genre
                    //load genre data
                    val songs = musicSongHelper?.getGenreSongs(event.genre.genreId,viewModelScope){
                        error = it.message
                    } ?: flowOf()
                    val albums = musicAlbumHelper?.getGenreAlbums(event.genre.genreId,viewModelScope){
                        error = it.message
                    } ?: flowOf()

                    genreData = GenreData(
                        genre = event.genre,
                        albums = albums,
                        songs = songs,

                    )
                    loading = false

                }
                MusicEvent.GoToDownloadedSermons -> {
                    navigationManager.navigate(SermonsDirections.downloaded_tab)
                }

                MusicEvent.LoadRecentActivities -> {
                    loading = true
                    recentActivities = recentActivityHelper.getRecentActivities(viewModelScope){
                        error = it.message
                    }
                    loading = false
                }

                is MusicEvent.ChangeShowingAlbum -> {
                    showingAlbum = event.show
                }

                is MusicEvent.ChangeShowingSong -> {
                    showingSong = event.show
                }

                MusicEvent.LoadLikedAlbums -> {

                }
                MusicEvent.GoToYears ->{
                    navigationManager.navigate(SermonsDirections.years)
                }
                MusicEvent.GoToYear -> {

                    navigationManager.navigate(SermonsDirections.year)
                }
                MusicEvent.LoadYears -> {
                    val c: Calendar = GregorianCalendar()
                    val upperBound = c.get(Calendar.YEAR)
                    years = Utilities.yearsRange(2010, upperBound)
                }
                is MusicEvent.YearSelected ->{
                     loading = true
                     selectedYear = event.year
                    Log.d("LoadByYear", "Selected")
                     yearSongs = musicSongHelper?.getSongsByYear(event.year,viewModelScope){
                         error = it.message
                     } ?: flowOf()
                    loading = false
                }
                else -> {

                }
            }
        }
    }



    private fun getImagePagers(){
        viewModelScope.launch {
            try {
                val result = newsInteractor.getImagePagers(1)
                _uiState.value = uiState.value.build {
                    musicPagerImages = result?.images?.map {
                        val imagePager = ImagePager.ModelMapper.withFullUrls(it)
                        imagePager.path
                    } ?: emptyList()
                }
            }
            catch (ex : Exception){
                Log.e("News",ex.message!!)
                _uiState.value = uiState.value.build {

                    error = ex.message!!
                }
            }
        }
    }

    private fun getTopSongs(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try{
                val res = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                    SongsSource(
                        musicInteractor = musicInteractor,
                        accessToken = accessToken!!,
                        songRequestType = SongRequestType.TOP_SONGS
                    )
                }.flow
                _uiState.value = uiState.value.build {
                    topSongsPaged = res
                    loading = false
                    error = null
                }
            }
            catch (ex : Exception){
                Log.e("Songs",ex.message!!)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message!!
                }
            }
        }
    }
    private fun getVideos() : Flow<PagingData<Video>> {

            val videosResult = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                VideoSource(
                    videoInteractor = videoInteractor,
                    accessToken = accessToken!!
                )
            }.flow

            return videosResult

         return flowOf(PagingData.from(VIDEOS))

    }
    private fun getForYou(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try{
                val res = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                    SongsSource(
                        musicInteractor = musicInteractor,
                        accessToken = accessToken!!,
                        songRequestType = SongRequestType.FOR_YOU
                    )
                }.flow
                _uiState.value = uiState.value.build {
                    forYouPaged = res
                    loading = false
                    error = null
                }
            }
            catch (ex : Exception){
                Log.e("Songs",ex.message!!)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message!!
                }
            }
        }
    }



    private  fun getGenres(callback : () -> Unit) {

        viewModelScope.launch{
            try {
                _uiState.value = uiState.value.build {
                    loading = true
                    error = null
                }
                val res = musicInteractor.getGenresGraph(accessToken!!)
                var genresResult = res?.genres?.map { g ->

                    Genre.ModelMapper.fromGenresGraph(g!!)

                }
                if (genresResult != null) {
                    _uiState.value = uiState.value.build {
                        genres = genresResult
                        loading = false
                    }
                    callback()
                } else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "No Genres found"
                    }
                }
            }catch (ex : Exception){
                Log.e("Genres",ex.message!!)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message!!
                }
            }

        }



    }

    private fun getTopAlbums(){

        viewModelScope.launch {
            try{
                _uiState.value = uiState.value.build {
                    isLoadingTopAlbums = true
                    error = null
                }
                val res = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                    AlbumsSource(
                        musicInteractor = musicInteractor,
                        accessToken = accessToken!!,
                        albumRequestType = AlbumRequestType.TOP_ALBUMS,
                        callback = {
                            _uiState.value = uiState.value.build {

                                isLoadingTopAlbums = false

                            }
                        }
                    )
                }.flow.cachedIn(viewModelScope)
                _uiState.value = uiState.value.build {
                    topAlbumsPaged = res
                    //isLoadingTopAlbums = false
                    error = null
                }
            }
            catch (ex : Exception){
                Log.e("Albums",ex.message!!)
                _uiState.value = uiState.value.build {
                    isLoadingTopAlbums = false
                    error = ex.message!!
                }
            }
        }
    }
}