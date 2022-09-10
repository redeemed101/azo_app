package com.fov.authentication.viewModels

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fov.authentication.models.UserModel
import com.fov.authentication.events.UsersEvent
import com.fov.authentication.pagination.UserNotificationSource
import com.fov.common_ui.utils.constants.UsersRequestType
import com.fov.authentication.states.UsersState
import com.fov.authentication.utils.constants.NotificationType
import com.fov.core.di.Preferences
import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.RecentUserSearch
import com.fov.domain.database.models.User
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.utils.constants.QueryConstants
import com.fov.navigation.HomeDirections
import com.fov.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val authenticate: Authenticate,
    private val userDao: UserDao,
    private val sharedPrefs: Preferences,
    private val navigationManager: NavigationManager
) :  ViewModel() {
    private val _uiState = MutableStateFlow(UsersState())
    val uiState: StateFlow<UsersState> = _uiState
    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()


   val notificationsPaging = Pager(PagingConfig(pageSize = QueryConstants.NUM_ROWS)) {
        UserNotificationSource(
            authenticate,
            userDao,
            NotificationType.UNREAD
        )
    }.flow
    init{
          viewModelScope.launch {

              var user = userDao.getUsers().first()?.first()
              if(user != null) {
                   val cur = getUserGraph(user.id) { cur ->
                       _uiState.value = uiState.value.build {
                           currentUserModel = cur
                           editUserModel = cur
                           userModel = cur
                       }
                   }

              }
          }

           getNumberNotifications()
    }

    private fun getNumberNotifications() {
       viewModelScope.launch {
           try{
               var user = userDao.getUsers().first().first()
               if(user != null) {

                   val res = authenticate.getNumberUnreadNotifications(user.id)
                   _uiState.value = uiState.value.build {
                       numNotifications = res
                   }
               }
           }
           catch(ex : Exception) {
                   val ex = ex.message
           }
       }
    }

    fun handleUsersEvent(event: UsersEvent) {
        if(event == UsersEvent.LoadNotifications){

        }
        else {
            _uiState.value = uiState.value.build {
                when (event) {



                    UsersEvent.LogoutUser -> {
                        viewModelScope.launch {

                            sharedPrefs.setAuthToken("")
                        }
                        logoutDone = true

                    }
                    UsersEvent.GoToProfile -> {
                        //navigate to profile
                        navigationManager.navigate(HomeDirections.profile)
                    }


                    else -> {

                    }
                }
                isEditUserContentValid = editUserModel?.name?.isNotEmpty() == true
                isEditUserContentValid = editUserModel?.email?.isNotEmpty() == true
                        && editUserModel?.phoneNumber?.isNotEmpty() == true
            }
        }
    }



    fun getUserGraph(userId : String,callback:(UserModel) -> Unit) {

        viewModelScope.launch {
            try {
                val userGraph = authenticate.getUserGraph(userId)
                if (userGraph != null) {
                    if(userGraph.user != null){
                         val user = UserModel.ModelMapper.fromGraph(userGraph)
                            _uiState.value.build {
                                userModel = user
                                loading = false
                            }
                            callback(user)

                    }
                }

            }
            catch(ex : Exception) {

            }
        }

    }

}