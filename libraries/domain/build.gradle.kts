import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.azo.buildsrc.*;
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(com.azo.buildsrc.Libs.Networking.GraphQL.plugin).version(com.azo.buildsrc.Libs.Networking.GraphQL.version)
    id("kotlin-android")
    id("kotlin-kapt")
    kotlin("plugin.serialization").version("1.7.10")
}
//apply(plugin = com.azo.buildsrc.Libs.Networking.GraphQL.plugin)
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
        getByName("debug") {
            buildConfigField("String", "FOV_URL", getProps("URL"))
            buildConfigField("String", "YOUTUBE_KEY", getProps("YOUTUBE_KEY"))
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
    implementation(Libs.AndroidX.Core.core)
    implementation(Libs.AndroidX.Core.compat)
    implementation(Libs.AndroidX.material)
    implementation(Libs.material)
    implementation(Libs.Kotlin.Coroutines.android)
    implementation(Libs.AndroidX.Paging.runtime)
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.Compose.paging)


    implementation(Libs.Networking.OkHttp.loggging)
    implementation(Libs.Networking.OkHttp.client)

    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.ktx)
    kapt(Libs.AndroidX.Room.kapt_compiler)
    implementation(Libs.AndroidX.Room.testing)
    annotationProcessor(Libs.AndroidX.Room.kapt_compiler)

    implementation(Libs.Serialization.gson)


    implementation(Libs.Networking.GraphQL.runtime)
    implementation(Libs.Networking.GraphQL.cache_sqlite)




    implementation(Libs.SkyDoves.sandwich)
    implementation(Libs.SkyDoves.whatif)

    implementation(Libs.Networking.Ktor.client)
    implementation(Libs.Networking.Ktor.negotiation)
    //implementation(Libs.Networking.Ktor.serialization)
    implementation(Libs.Networking.Ktor.kotlinx_json)
    implementation(Libs.Networking.Ktor.logging)
    implementation(Libs.Networking.Ktor.auth)
    implementation(Libs.Networking.Ktor.mock)


    testImplementation(Libs.Testing.junit)
    androidTestImplementation(Libs.Testing.junit_ext)
    androidTestImplementation(Libs.Testing.espresso)

    implementation(Libs.slf4j)
}
fun getProps(propName : String) : String{
   return gradleLocalProperties(rootDir).getProperty(propName)
}
apollo {

    service("albums") {
        sourceFolder.set("albums")
        packageName.set("com.fov.domain.albums")
    }
    service("genres") {
        sourceFolder.set("genres")
        packageName.set("com.fov.domain.genres")
    }
    service("users") {
        sourceFolder.set("users")
        packageName.set("com.fov.domain.users")
    }

    service("music") {
        sourceFolder.set("music")
        packageName.set("com.fov.domain.music")
    }

}
