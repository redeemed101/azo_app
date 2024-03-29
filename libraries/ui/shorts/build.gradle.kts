import com.azo.buildsrc.*;
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("plugin.serialization").version("1.7.10")
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
    buildFeatures {
        compose  = true
        viewBinding =  true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  Versions.Compose.version


    }
}

dependencies {
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
    implementation(Libs.AndroidX.Compose.paging)
    implementation(Libs.AndroidX.Compose.navigation)

    androidTestImplementation(Libs.AndroidX.Compose.ui_test)
    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junit_ext)
    androidTestImplementation(Libs.Testing.espresso)
    implementation(Libs.AndroidX.Compose.view_binding)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation (Libs.AndroidX.Lifecycle.liveData)
    implementation (Libs.AndroidX.Lifecycle.lifecycleRuntime)
    implementation (Libs.AndroidX.Lifecycle.viewModelSavedState)
    testImplementation (Libs.AndroidX.Core.testImplementation)
    implementation (Libs.AndroidX.Core.start_runtime)

    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    compileOnly(Libs.AssistedInjection.dagger)
    kapt(Libs.Hilt.compiler)
    kapt(Libs.Hilt.AndroidX.compiler)
    kapt(Libs.AssistedInjection.processor)
}