import com.azo.buildsrc.*;
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}


android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose  = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  Versions.Compose.version

    }

}

dependencies{
    implementation(project(":libraries:core"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:ui:common_ui"))

    implementation(Libs.AndroidX.Core.core)
    implementation(Libs.AndroidX.Core.compat)
    implementation(Libs.material)
    implementation (Libs.AndroidX.Compose.compiler)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntime)
    implementation(Libs.AndroidX.Compose.activity)
    implementation(Libs.Layout.constraint)
    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junit_ext)
    androidTestImplementation(Libs.Testing.espresso)
    implementation(Libs.Kotlin.Coroutines.android)
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Facebook.facebook_login)
    implementation(Libs.Facebook.facebook_sdk)
    implementation(Libs.Google.auth)
    implementation(Libs.Google.firebase_messaging)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    implementation(Libs.AndroidX.Compose.animation)
    implementation(Libs.AndroidX.Lifecycle.viewmodel_lifecycle)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Paging.runtime)
    implementation(Libs.AndroidX.Compose.paging)

    implementation(Libs.Accompanist.pager)
    implementation(Libs.Accompanist.pager_indicators)

    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    //implementation(Libs.Hilt.AndroidX.viewModel)
    compileOnly(Libs.AssistedInjection.dagger)
    kapt(Libs.Hilt.compiler)
    kapt(Libs.Hilt.AndroidX.compiler)
    kapt(Libs.AssistedInjection.processor)


    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntime)
    implementation(Libs.AndroidX.Lifecycle.viewModelSavedState)
    testImplementation(Libs.AndroidX.Core.testImplementation)
    implementation(Libs.AndroidX.Core.start_runtime)

    compileOnly(Libs.AssistedInjection.dagger)
    kapt(Libs.AssistedInjection.processor)


    implementation(Libs.Networking.OkHttp.loggging)
    implementation(Libs.Networking.OkHttp.client)
    implementation(Libs.coil)
    implementation(Libs.Accompanist.insets)
    implementation(Libs.Accompanist.swipe)
    androidTestImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.Kotlin.Coroutines.test)
    androidTestImplementation(Libs.AndroidX.Compose.ui_test)

    implementation(Libs.Networking.GraphQL.runtime)

    kaptAndroidTest(Libs.AssistedInjection.processor)
    implementation(Libs.slf4j)

}