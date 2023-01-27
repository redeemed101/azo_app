package com.fov.azo.di


import android.app.Application
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.azo.FcmService
import com.fov.core.di.Preferences
import com.fov.core.files.FileProcessor
import com.fov.core.security.encryption.Encryption
import com.fov.core.security.encryption.KeyGeneration
import com.fov.core.security.fileEncryption.FileEncryption
import com.fov.core.utils.NetworkWatcher
import com.fov.core.utils.Utilities
import com.fov.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesNavigationManager() = NavigationManager()

    //@Singleton
    //@Provides
    //fun providesFCMService() = FcmService()

    //@Singleton
    @Provides
    fun providesNetworkWatcher(application: Application) = NetworkWatcher(application)

    @Singleton
    @Provides
    fun providesUtilities(preferences: Preferences) = Utilities(preferences = preferences)

    @Singleton
    @Provides
    fun providesKeyGeneration(preferences: Preferences)  =  KeyGeneration(preferences)

    @Singleton
    @Provides
    fun providesEncryption()  = Encryption()

    @Singleton
    @Provides
    fun providesFileProcessor() =  FileProcessor()

    @Singleton
    @Provides
    fun providesFileEncryption(encryption : Encryption,  fileProcessor: FileProcessor)
        = FileEncryption(encryption,fileProcessor)

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main



}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher