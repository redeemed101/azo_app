import com.azo.buildsrc.*
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

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
    composeOptions {
        kotlinCompilerExtensionVersion =  Versions.Compose.version
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:ui:common_ui"))
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
    implementation(Libs.coil)

    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    compileOnly(Libs.AssistedInjection.dagger)
    kapt(Libs.Hilt.compiler)
    kapt(Libs.Hilt.AndroidX.compiler)
    kapt(Libs.AssistedInjection.processor)
    implementation(Libs.Networking.GraphQL.runtime)

    implementation(Libs.AndroidX.Lifecycle.viewmodel_lifecycle)

    androidTestImplementation(Libs.AndroidX.Compose.ui_test)
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    implementation(Libs.AndroidX.Compose.animation)
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

    implementation (Libs.Stripe.stripe)

    implementation(Libs.AndroidX.Core.worker_runtime)
    implementation (Libs.AndroidX.Core.worker_runtime_ktx)
}