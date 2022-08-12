package com.fov.sermons.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fov.sermons.states.MusicState
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SermonViewModel @Inject constructor(
    private val musicInteractor: MusicInteractor,
    private val playlistInteractor: PlaylistInteractor,
    private val navigationManager: NavigationManager
) : ViewModel(){
    private val _uiState = MutableStateFlow(MusicState())
    val uiState: StateFlow<MusicState> = _uiState
    private val musicPlaylistHelper : MusicPlaylistHelper = MusicPlaylistHelper(musicInteractor)
    private val playlistHelper : PlaylistHelper = PlaylistHelper(playlistInteractor = playlistInteractor)
    private val musicAlbumHelper  = MusicAlbumHelper(musicInteractor = musicInteractor)
    private val musicSongHelper = MusicSongHelper(musicInteractor)
    private val musicArtistHelper = MusicArtistHelper(musicInteractor)
    private val recentActivityHelper = RecentActivityHelper(musicInteractor)
    init {

        getTopSongs()
        getTopAlbums()
        getForYou()
    }

    fun isSongLiked(song: Song, userId: String) : Boolean {
        return song.userLikes.contains(userId)
    }

    fun handleMusicEvent(event: MusicEvent) {
        _uiState.value = uiState.value.build {

            when (event) {
                MusicEvent.LoadTopSongs ->{
                    getTopSongs()

                }
                is MusicEvent.GetSong ->{
                    viewModelScope.launch {
                        val song = musicSongHelper.getSong(event.songId)
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
                MusicEvent.LoadHome -> {
                    newSongs = musicSongHelper.getNewSongs(viewModelScope){
                        error = it.message
                    }
                    newAlbums = musicAlbumHelper.getNewAlbums(viewModelScope){
                        error = it.message
                    }

                    trendingArtists = musicArtistHelper.getTrendingArtists(viewModelScope){
                        error = it.message
                    }


                }
                MusicEvent.GoToGenres ->{
                    navigationManager.navigate(MusicDirections.genres)
                }
                MusicEvent.LoadGenres -> {

                    loading = true
                    getGenres{
                        loading = false
                    }



                }
                is MusicEvent.GetArtistSongs -> {
                    artistSongs =  musicSongHelper.getArtistSongs(event.artistId, viewModelScope){
                        error = it.message
                    }
                }

                is MusicEvent.SearchSongTextChanged -> {
                    searchSongText = event.search
                }

                is MusicEvent.SearchSong ->{
                    loading = true
                    recentSongSearch = musicSongHelper.searchSongs(event.search,viewModelScope){
                        error = it.message
                    }
                    loading = false
                }
                MusicEvent.LoadRecentSearch ->{
                    loading = true
                    recentSongSearch = musicSongHelper.getRecentSongSearch(viewModelScope){
                        error = it.message
                    }
                    loading = false
                }

                MusicEvent.LoadTopAlbums -> {
                    getTopAlbums()
                }
                MusicEvent.LoadForYou -> {
                    getForYou()
                }
                MusicEvent.ClearRecentSongSearch -> {
                    musicSongHelper.deleteRecentSongSearch(viewModelScope){

                    }
                }
                is MusicEvent.SaveRecentSearch -> {
                    musicSongHelper.saveRecentSongSearch(event.song,viewModelScope){

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
                    navigationManager.navigate(MusicDirections.album)
                }
                is MusicEvent.ChangeSongSelected -> {
                    selectedSong = event.song
                }
                is MusicEvent.MinimizeMusicPlayer -> {
                    isPlayerMinimized = event.minimized
                }
                is MusicEvent.PlaySong -> {
                    currentSong = event.song
                    navigationManager.navigate(MusicDirections.playSong)
                }
                is MusicEvent.SongStarted ->{
                    songStarted = event.started
                }
                MusicEvent.NavigateToHome ->{
                    navigationManager.navigate(MusicDirections.tab)
                }
                is MusicEvent.SetMediaPlayback ->{
                    mediaPlayBack = event.player
                }
                is MusicEvent.AddToCurrentPlaylist-> {
                    val item = MediaItem.fromUri( Uri.parse(
                        event.url
                    ))
                    if(mediaPlayBack != null)
                        mediaPlayBack!!.addMediaToPlaylist(item)
                }
                is MusicEvent.AddToNowPlaying -> {
                    val song = event.song
                    //added
                    currentSong = event.song
                    isPlayerMinimized = false
                    songStarted = false
                    //end of added
                    var currentList: MutableList<Song> = mutableListOf()
                    if(nowPlaying.isNotEmpty())
                        currentList = nowPlaying as MutableList<Song>
                    currentList.add(song)
                    nowPlaying = currentList
                }
                is MusicEvent.SongSelected -> {
                    val song = event.song
                    selectedSong = song
                    //add to recent activity
                    recentActivityHelper.saveRecentActivity(
                        RecentActivity(
                            id = song.songId,
                            title = song.songName,
                            subTitle = song.artistName,
                            type = ActivityType.SONG.type,
                            image = song.artwork
                        ),
                        viewModelScope
                    ){

                    }
                    //navigate
                    navigationManager.navigate(MusicDirections.song)
                }
                MusicEvent.GoToGenre -> {
                    //navigate to genre
                    navigationManager.navigate(MusicDirections.genre)
                }
                MusicEvent.LoadLikedMusic -> {
                    val likedAlbums = musicAlbumHelper.getUserLikedAlbums("userId",viewModelScope){
                        error = it.message
                    }
                    val  likedSongs = musicSongHelper.getLikedSongs("userId",viewModelScope){
                        error = it.message
                    }
                    likedMusicData = LikedMusicData(
                        likedAlbums = likedAlbums,
                        likedSongs = likedSongs
                    )
                }
                MusicEvent.GoToLikedMusic ->{
                    val likedAlbums = musicAlbumHelper.getUserLikedAlbums("userId",viewModelScope){
                        error = it.message
                    }
                    val  likedSongs = musicSongHelper.getLikedSongs("userId",viewModelScope){
                        error = it.message
                    }
                    likedMusicData = LikedMusicData(
                        likedAlbums = likedAlbums,
                        likedSongs = likedSongs
                    )
                    navigationManager.navigate(CollectionDirections.likedMusic)
                }
                is MusicEvent.GenreSelected -> {
                    loading = true
                    selectedGenre = event.genre
                    //load genre data
                    val songs = musicSongHelper.getGenreSongs(event.genre.genreId,viewModelScope){
                        error = it.message
                    }
                    val albums = musicAlbumHelper.getGenreAlbums(event.genre.genreId,viewModelScope){
                        error = it.message
                    }
                    val artists = musicArtistHelper.getGenreArtists(event.genre.genreId,viewModelScope){
                        error = it.message
                    }
                    val playlists = playlistHelper.getGenrePlaylists(event.genre.genreId,viewModelScope){
                        error = it.message
                    }
                    genreData = GenreData(
                        genre = event.genre,
                        albums = albums,
                        songs = songs,
                        artists = artists,
                        playlists = playlists

                    )
                    loading = false

                }
                MusicEvent.GoToCharts -> {
                    navigationManager.navigate(MusicDirections.charts)
                }
                MusicEvent.LoadCharts -> {
                    loading = true
                    val trendingArtists = musicArtistHelper.getTrendingArtists(viewModelScope){
                        error = it.message
                    }
                    val dailyCharts = playlistHelper.getDailyChartPlaylists(viewModelScope){
                        error = it.message
                    }
                    val weeklyCharts = playlistHelper.getWeeklyChartPlaylists(viewModelScope){
                        error = it.message
                    }
                    chartsData = ChartsData(
                        trendingArtists = trendingArtists,
                        dailyCharts = dailyCharts,
                        weeklyCharts = weeklyCharts
                    )
                    loading = false

                }
                MusicEvent.LoadMoods -> {
                    loading = true
                    moods = musicPlaylistHelper.getMoods(viewModelScope){
                        error = it.message
                    }
                    loading = false

                }
                MusicEvent.GoToMoods -> {
                    navigationManager.navigate(MusicDirections.moods)
                }
                MusicEvent.GoToPlaylists -> {
                    navigationManager.navigate(MusicDirections.playlists)
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

                MusicEvent.CreateNewPlaylist -> {

                }
                is MusicEvent.ChangeShowingSong -> {
                    showingSong = event.show
                }
                is MusicEvent.MoodSelected -> {
                    loading = true
                    val moodPlaylists = playlistHelper.getMoodPlaylists(event.mood.id,viewModelScope){
                        error = it.message
                    }
                    loading = false
                    selectedMood = SelectedMoodData(
                        mood = event.mood,
                        playlists = moodPlaylists
                    )
                    navigationManager.navigate(MusicDirections.mood)

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
                        SongRequestType.TOP_SONGS
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
                        SongRequestType.FOR_YOU
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


    private fun getPlaylist(id :String){
        viewModelScope.launch {
            musicInteractor.getPlaylist(id)
        }
    }
    private  fun getGenres(callback : () -> Unit) {

        viewModelScope.launch{
            _uiState.value = uiState.value.build {
                loading = true
                error = null
            }
            val res = musicInteractor.getGenresGraph()
            var genresResult = res?.genres()?.map { g ->

                Genre.ModelMapper.fromGenresGraph(g)

            }
            if(genresResult != null){
                _uiState.value = uiState.value.build {
                    genres = genresResult
                    loading = false
                }
                callback()
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "No Genres found"
                }
            }

        }



    }

    private fun getTopAlbums(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try{
                val res = Pager(PagingConfig(pageSize = Constants.NUM_PAGE)) {
                    AlbumsSource(
                        musicInteractor = musicInteractor,
                        AlbumRequestType.TOP_ALBUMS
                    )
                }.flow
                _uiState.value = uiState.value.build {
                    topAlbumsPaged = res
                    loading = false
                    error = null
                }
            }
            catch (ex : Exception){
                Log.e("Albums",ex.message!!)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message!!
                }
            }
        }
    }
}