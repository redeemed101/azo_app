import com.azo.buildsrc.*;

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization").version("1.7.10")
}

kapt {
    correctErrorTypes =  true
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "FOV_URL", getProps("URL"))
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

dependencies {
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:ui:common_ui"))
    implementation(project(":libraries:ui:shorts"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:ui:authentication"))
    implementation(project(":libraries:ui:sermons"))

    //implementation(Libs.Networking.Ktor.serialization)
     implementation(Libs.Networking.Ktor.kotlinx_json)
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation(Libs.AndroidX.Core.core)
    implementation(Libs.AndroidX.Core.compat)
    implementation(Libs.material)
    implementation(Libs.AndroidX.Compose.animation)
    implementation(Libs.Accompanist.navigation)
    implementation (Libs.AndroidX.Compose.compiler)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.material_icons)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.activity)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Compose.paging)

    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntime)
    implementation(Libs.AndroidX.Lifecycle.viewModelSavedState)
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Kotlin.Coroutines.android)

    implementation(Libs.AndroidX.Camera.core)
    implementation(Libs.AndroidX.Camera.camera2)
    implementation(Libs.AndroidX.Camera.lifecycle)
    implementation(Libs.AndroidX.Camera.view)
    implementation(Libs.AndroidX.DataStore.core)
    implementation(Libs.AndroidX.DataStore.preferences)
    implementation(Libs.AndroidX.DataStore.typed)

    implementation(Libs.Networking.GraphQL.runtime)
    implementation(Libs.Google.ExoPlayer.expo)

    implementation(Libs.Accompanist.swipe)
    implementation(Libs.Accompanist.pager)
    implementation(Libs.Accompanist.pager_indicators)

    implementation(Libs.coil)
    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    //implementation(Libs.Hilt.AndroidX.viewModel)
    compileOnly(Libs.AssistedInjection.dagger)

    implementation(Libs.Networking.Ktor.client)
    implementation(Libs.Networking.Ktor.negotiation)
    implementation(Libs.Networking.Ktor.kotlinx_json)
    implementation(Libs.Networking.Ktor.logging)


    implementation(Libs.Facebook.facebook_login)
    implementation(Libs.Facebook.facebook_sdk)
    implementation(Libs.Google.auth)
    implementation(Libs.Google.firebase_messaging)
    implementation(Libs.Google.location)

    implementation(Libs.Networking.OkHttp.loggging)
    implementation(Libs.Networking.OkHttp.client)

    implementation(Libs.AndroidX.Room.runtime)
    kapt(Libs.AndroidX.Room.kapt_compiler)
    implementation (Libs.AndroidX.Room.ktx)
    implementation(Libs.AndroidX.Room.testing)

    kapt(Libs.Hilt.compiler)
    kapt (Libs.Hilt.AndroidX.compiler)
    kapt (Libs.AssistedInjection.processor)
    androidTestImplementation(Libs.AndroidX.Compose.ui_test)
    androidTestImplementation(Libs.Kotlin.Coroutines.test)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    implementation(Libs.AndroidX.Compose.animation)
    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junit_ext)
    androidTestImplementation(Libs.Testing.espresso)
}
fun getProps(propName : String) : String{
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty(propName)
}


