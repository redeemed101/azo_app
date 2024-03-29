import com.azo.buildsrc.*;

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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

dependencies {
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:core"))

    implementation(Libs.AndroidX.Core.core)
    implementation(Libs.AndroidX.Core.compat)
    implementation(Libs.material)
    implementation (Libs.AndroidX.Compose.compiler)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.activity)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Compose.paging)

    implementation(Libs.AndroidX.Camera.core)
    implementation (Libs.AndroidX.Camera.camera2)
    implementation(Libs.AndroidX.Camera.lifecycle)
    implementation (Libs.AndroidX.Camera.view)

    implementation(Libs.Networking.GraphQL.runtime)
    implementation(Libs.AndroidX.Core.palette)

    implementation(Libs.AndroidX.Core.worker_runtime)
    implementation(Libs.AndroidX.Core.worker_runtime_ktx)

    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntime)


    implementation(Libs.coil)

    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    implementation(Libs.Hilt.AndroidX.worker)
    compileOnly(Libs.AssistedInjection.dagger)
    kapt(Libs.Hilt.compiler)
    kapt(Libs.Hilt.AndroidX.compiler)
    kapt(Libs.AssistedInjection.processor)

    androidTestImplementation(Libs.AndroidX.Compose.ui_test)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    implementation(Libs.AndroidX.Compose.animation)
    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junit_ext)
    androidTestImplementation(Libs.Testing.espresso)
}