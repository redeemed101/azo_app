package com.fov.azo

import android.annotation.SuppressLint
import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.workers.DeleteOldFilesWorker
import com.fov.core.di.Preferences
import com.fov.domain.BuildConfig
import com.fov.domain.database.models.User
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AzoApplication: Application(),  CameraXConfig.Provider, Configuration.Provider {

    //val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    var user : User? = null

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()



    @SuppressLint("NewApi")
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
    override fun onCreate() {
        super.onCreate()
        var basePath = "${
            com.fov.common_ui.utils.helpers.Utilities
                .getCacheDirectory(
                    applicationContext
                ).absolutePath}"
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
        val inputData = Data.Builder()
            .putString(Constants.HOW_OLD_DAYS,"2")
            .putString(Constants.DIRECTORY,basePath)
            .putString(Constants.FILE_EXTENSION,"mp3")
            .build()
        val myWork = PeriodicWorkRequest.Builder(DeleteOldFilesWorker::class.java,
            1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DeleteOldFilesWorker",
        ExistingPeriodicWorkPolicy.KEEP,
        myWork)
        PaymentConfiguration.init(
            applicationContext,
            publishableKey = BuildConfig.STRIPE_KEY,
            stripeAccountId = BuildConfig.STRIPE_ACCOUNT_ID
        )
    }

}