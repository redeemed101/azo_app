package com.fov.azo

import android.annotation.SuppressLint
import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.fov.core.di.Preferences
import com.fov.domain.BuildConfig
import com.fov.domain.database.models.User
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AzoApplication: Application(),  CameraXConfig.Provider {

    //val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    var user : User? = null

    @SuppressLint("NewApi")
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            publishableKey = BuildConfig.STRIPE_KEY,
            stripeAccountId = BuildConfig.STRIPE_ACCOUNT_ID
        )
    }

}