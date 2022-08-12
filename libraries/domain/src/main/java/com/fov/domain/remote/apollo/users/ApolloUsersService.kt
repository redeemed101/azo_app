package com.fov.domain.remote.apollo.users

import com.apollographql.apollo.coroutines.await
import com.fov.domain.users.GetPaginatedUsersQuery
import com.fov.domain.users.GetUserQuery

interface ApolloUsersService {
    suspend fun getUser(id :String): GetUserQuery.Data?
    suspend fun getPaginatedUsers(page : Int, size :  Int): GetPaginatedUsersQuery.Data?
}