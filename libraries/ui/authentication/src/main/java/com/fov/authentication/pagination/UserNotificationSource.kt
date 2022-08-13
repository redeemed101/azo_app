package com.fov.authentication.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.authentication.models.Notification
import com.fov.authentication.utils.constants.NotificationType
import com.fov.domain.database.daos.UserDao
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.models.users.notifications.NotificationsResult
import kotlinx.coroutines.flow.first

class UserNotificationSource constructor(
    private val authenticate: Authenticate,
    private val userDao: UserDao ,
    private val notificationType : NotificationType
) : PagingSource<Int, Notification>() {
    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        return try {
            val nextPage = params.key ?: 1
            var notificationsResult : NotificationsResult? = null
            var unread = false
            if(notificationType == NotificationType.UNREAD){
                unread = true
            }
            var userId = ""

            var user = userDao.getUsers().first().first()
            if(user != null)
                    userId = user.id

            notificationsResult = authenticate.getUserNotifications(userId,page = nextPage,unread)
            if(notificationsResult != null) {

                    LoadResult.Page(
                        data = notificationsResult!!.notifications.map{ notification ->
                            Notification.ModelMapper.from(notification)
                        },
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = nextPage.plus(1)
                    )

            }
            else{
                LoadResult.Error(Exception(""))
            }
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}