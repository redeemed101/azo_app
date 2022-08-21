package com.fov.azo

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.fov.core.di.Preferences
import com.fov.domain.database.models.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AzoApplication: Application(),  CameraXConfig.Provider {

    //val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    var user : User? = null

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

}