package com.fov.authentication.di

import com.fov.domain.cache.Cache
import com.fov.domain.database.daos.UserDao
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.remote.apollo.users.ApolloUsersService
import com.fov.domain.remote.authentication.*
import com.fov.domain.repositories.authentication.AuthenticationDataRepository
import com.fov.domain.repositories.authentication.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    fun providesAuthenticate(
        repository: AuthenticationRepository,
        graphQLService : ApolloUsersService,
        userDao: UserDao
    ): Authenticate = Authenticate(repository, graphQLService, userDao)

    @Provides
    fun providesAuthenticationRepository(
        remote: AuthenticationRemote,
        cache: Cache
    ): AuthenticationRepository = AuthenticationDataRepository(remote,cache)


    @Provides
    fun providesAuthenticationRemote(
        authenticationService : AuthenticationKtorService
    ): AuthenticationRemote = AuthenticationRemoteKtorStore(
        authenticationService
    )
}

