import com.azo.buildsrc.*;

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    //id(com.azo.buildsrc.Libs.Networking.GraphQL.plugin).version(com.azo.buildsrc.Libs.Networking.GraphQL.version)
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

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
}

dependencies {
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:ui:common_ui"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:domain"))
    implementation(project(":libraries:ui:authentication"))
    implementation(project(":libraries:ui:sermons"))

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

    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntime)
    implementation(Libs.AndroidX.Lifecycle.viewModelSavedState)
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Kotlin.Coroutines.android)

    implementation(Libs.Networking.GraphQL.runtime)

    implementation(Libs.coil)
    implementation(Libs.Hilt.AndroidX.compose_navigation)
    implementation(Libs.Hilt.android)
    implementation(Libs.Hilt.AndroidX.viewModel)
    compileOnly(Libs.AssistedInjection.dagger)

    implementation(Libs.Networking.Ktor.client)
    implementation(Libs.Networking.Ktor.serialization)
    implementation(Libs.Networking.Ktor.kotlinx_json)
    implementation(Libs.Networking.Ktor.logging)

    implementation(Libs.Networking.OkHttp.loggging)
    implementation(Libs.Networking.OkHttp.client)

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


