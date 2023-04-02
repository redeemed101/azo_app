package com.fov.main.infrastructure.di

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.fov.core.BuildConfig
import com.fov.core.utils.Utilities
import com.fov.domain.remote.AuthInterceptor
import com.fov.domain.remote.RequestInterceptor
import com.fov.domain.remote.apollo.ApolloSetup
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.remote.apollo.music.ApolloMusicServiceImpl
import com.fov.domain.remote.apollo.music.ApolloMusicServiceTestImpl
import com.fov.domain.remote.apollo.users.ApolloUsersService
import com.fov.domain.remote.apollo.users.ApolloUsersServiceImpl
import com.fov.domain.remote.apollo.users.ApolloUsersServiceTestImpl
import com.fov.domain.remote.authentication.AuthenticationKtorService
import com.fov.domain.remote.ktor.KtorClient
import com.fov.domain.remote.mock.KtorMockClient
import com.fov.domain.remote.music.MusicKtorService
import com.fov.domain.remote.news.NewsKtorService
import com.fov.domain.remote.payment.PaymentKtorService
import com.fov.domain.remote.video.VideoKtorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
   fun provideOkHttpClient(@ApplicationContext context: Context,
                                    utilities: Utilities): OkHttpClient {
        //val token = utilities.getToken()
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(AuthInterceptor("Bearer",""))
            //.cache(CoilUtils.createDefaultCache(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }


    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header("Authorization", "")
                chain.proceed(builder.build())
            }
        return ApolloClient.Builder()
            .serverUrl("${BuildConfig.FOV_URL}users/graphql")
            .okHttpClient(okHttp.build()) //ApolloClient with okhttp
            .build()
    }
    @Provides
    @Singleton
    fun providesApolloSetup() : ApolloSetup{
        return ApolloSetup()
    }
    @Provides
    @Singleton
    fun providesApolloUsersService(apolloSetup: ApolloSetup)  : ApolloUsersService {
        return if(BuildConfig.BUILD_TYPE.lowercase() == "debug") {
            ApolloUsersServiceTestImpl(apolloSetup)
        } else{
            ApolloUsersServiceImpl(apolloSetup)
        }
    }
    @Provides
    @Singleton
    fun providesApolloMusicService(apolloSetup: ApolloSetup)  : ApolloMusicService {
        return if(BuildConfig.BUILD_TYPE.lowercase() == "debug") {
            ApolloMusicServiceTestImpl(apolloSetup)
        } else {
            ApolloMusicServiceImpl(apolloSetup)
        }
    }


    @Provides
    @Singleton
    fun providesKtorClient() : HttpClient {

        return if( BuildConfig.BUILD_TYPE.lowercase() == "debug") {
            //KtorMockClient.ktorHttpClient
            KtorClient.ktorHttpClient
        } else{
            KtorClient.ktorHttpClient
        }

    }

    @Provides
    @Singleton
    fun providesKtorAuthService(client : HttpClient) : AuthenticationKtorService {

        return AuthenticationKtorService(client)
    }
    @Provides
    @Singleton
    fun providesMusicKtorService(client : HttpClient) : MusicKtorService {

        return MusicKtorService(client)
    }

    @Provides
    @Singleton
    fun providesPaymentKtorService(client : HttpClient) : PaymentKtorService {

        return PaymentKtorService(client)
    }


    @Provides
    @Singleton
    fun providesVideoKtorService(client : HttpClient) : VideoKtorService {

        return VideoKtorService(client)
    }

    @Provides
    @Singleton
    fun providesNewsKtorService(client : HttpClient) : NewsKtorService {

        return NewsKtorService(client)
    }




}