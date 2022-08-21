package com.fov.domain.remote.apollo.users

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.users.GetPaginatedUsersQuery
import com.fov.domain.users.GetUserQuery
import com.fov.domain.remote.apollo.users.ApolloUsersService

class ApolloUsersServiceImpl constructor(
    apolloSetup : ApolloSetup
) : ApolloUsersService {
    private var apolloClient: ApolloClient = apolloSetup.setUpApolloClient("/users/graphql")
    override suspend fun getUser(id :String): GetUserQuery.Data? {
        val res = apolloClient.query(GetUserQuery(Optional.presentIfNotNull(id))).execute()
        return res.data
    }
    override suspend fun getPaginatedUsers(page : Int, size :  Int): GetPaginatedUsersQuery.Data? {
         val res = apolloClient.query(GetPaginatedUsersQuery(Optional.presentIfNotNull(page)
             ,Optional.presentIfNotNull(size))
         ).execute()
        return res.data
    }
}