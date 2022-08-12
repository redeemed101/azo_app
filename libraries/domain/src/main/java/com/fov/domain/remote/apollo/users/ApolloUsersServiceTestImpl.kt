package com.fov.domain.remote.apollo.users

import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.users.GetPaginatedUsersQuery
import com.fov.domain.users.GetUserQuery
import com.fov.domain.remote.apollo.users.ApolloUsersService

class ApolloUsersServiceTestImpl  constructor(
    private val apolloSetup : ApolloSetup
) : ApolloUsersService {
    override suspend fun getUser(id: String): GetUserQuery.Data? {
        return GetUserQuery.Data(
            GetUserQuery.User(
                "User",
                "sdfsdfsdf",
                "Lewis Msasa",
                "Lewis is amazing",
                "lmsasajnr",
                "https://picsum.photos/200",
                "265881286653",
                "lmsasajnr@gmail.com",
                true,
                "Public Figure",
                "Lilongwe, Malawi, Africa",
                "www.lewischase.com",
                "1994-30-10",
                "Male",
            ),
            GetUserQuery.Country(
                "Country",
                "Malawi"
            ),
            List(20){
                GetUserQuery.FullFollower(
             "FullFollower",
                    "12345",
                    "Godmond",
                    "go",
                    "https://picsum.photos/200",
                    "go@gmail.com",

                )
            },
            List(20){
                GetUserQuery.FullFollowing(
                    "FullFollowing",
                    "12345",
                    "Godmond",
                    "go",
                    "https://picsum.photos/200",
                    "go@gmail.com",
                )
            }

        )
    }

    override suspend fun getPaginatedUsers(page: Int, size: Int): GetPaginatedUsersQuery.Data? {
        return GetPaginatedUsersQuery.Data(
            List(20) {
                GetPaginatedUsersQuery.UsersPaginated(
                    "User",
                    "1234",
                    "Lewis Msasa",
                    "lmsasajnr",
                    "https://picsum.photos/200",
                    "https://picsum.photos/200",
                    false,
                    "Public Figure",
                    "Lilongwe, Malawi",
                    "www.lewischase.com",
                    "1994-30-10",
                    "Male",

                )
            }
        )
    }
}