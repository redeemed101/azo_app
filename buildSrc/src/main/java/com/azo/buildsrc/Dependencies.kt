package com.azo.buildsrc

object Libs {
    const val slf4j = "org.slf4j:slf4j-jdk14:1.7.25"
    const val material = "com.google.android.material:material:1.6.1"
    const val coil = "io.coil-kt:coil-compose:2.1.0"
    const val prettyTime = "org.ocpsoft.prettytime:prettytime:4.0.4.Final"
    object Glide{
        const val glide = "com.github.bumptech.glide:glide:4.14.2"
        const val processor = "com.github.bumptech.glide:compiler:4.14.2"
    }
    object Stripe{
        const val stripe = "com.stripe:stripe-android:20.11.0"
    }
    object Youtube{
        const val youtubePlayer = "com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0"
    }
    object Serialization{
       private const val version = "2.9.1"
       const val gson = "com.google.code.gson:gson:$version"
    }
    object SkyDoves{
        private const val version = "1.1.0"
        const val whatif = "com.github.skydoves:sandwich:$version"
        const val sandwich = "com.github.skydoves:whatif:$version"
    }
    object Testing{
        const val junit = "junit:junit:4.13.2"
        const val ver = "1.1.3"
        const val junit_ext = "androidx.test.ext:junit:$ver"
        const val junit_ktx = "androidx.test.ext:junit-ktx:$ver"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
    object Facebook{
        private const val version = "latest.release"
        const val facebook_login = "com.facebook.android:facebook-login:$version"
        const val facebook_sdk = "com.facebook.android:facebook-android-sdk:5.4.0"
    }
    object Google{
        private const val version = "20.0.0"
        const val auth = "com.google.android.gms:play-services-auth:$version"
        const val plugin = "com.google.gms:google-services:4.3.13"
        const val plugin_no_version = "com.google.gms.google-services"
        const val firebase_messaging = "com.google.firebase:firebase-messaging:$version"
        const val location = "com.google.android.gms:play-services-location:$version"

        object ExoPlayer{
            private const  val version = "2.18.1"
            const val expo = "com.google.android.exoplayer:exoplayer:$version"
            const val core = "com.google.android.exoplayer:exoplayer-core:$version"
            const val dash = "com.google.android.exoplayer:exoplayer-dash:$version"
            const val ui =  "com.google.android.exoplayer:exoplayer-ui:$version"
        }


    }


    object Accompanist {
        private const val version = "0.25.0"
        const val coil = "com.google.accompanist:accompanist-coil:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val swipe = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val navigation = "com.google.accompanist:accompanist-navigation-animation:0.16.0"
        const val permissions  = "com.google.accompanist:accompanist-permissions:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val pager_indicators = "com.google.accompanist:accompanist-pager-indicators:$version"
    }

    object Kotlin {
         const val version = "1.7.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"
        object Coroutines {

            private const val version = "1.6.4"
            const val core ="org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }
    object Networking{
        object Ktor{
            private const val version = "2.1.0"
            const val client =  "io.ktor:ktor-client-android:$version"
            const val serialization =  "io.ktor:ktor-client-serialization:$version"
            const val auth = "io.ktor:ktor-client-auth:$version"
            const val okHttp = "io.ktor:ktor-client-okhttp:$version"
            const val kotlinx_json =  "io.ktor:ktor-serialization-kotlinx-json:$version"
            const val logging = "io.ktor:ktor-client-logging-jvm:$version"
            const val negotiation = "io.ktor:ktor-client-content-negotiation:$version"
            const val mock = "io.ktor:ktor-client-mock:$version"
        }

        object OkHttp{
            private const val version = "4.10.0"
            const val loggging = "com.squareup.okhttp3:logging-interceptor:${version}"
            const val client = "com.squareup.okhttp3:okhttp:${version}"
        }

        object GraphQL {
            const val version = "3.5.0"
            const val plugin = "com.apollographql.apollo3"
            const val runtime =  "com.apollographql.apollo3:apollo-runtime"
            const val support =  "com.apollographql.apollo3:apollo-coroutines-support:3.4.0"
            const val gradlePlugin = "com.apollographql.apollo3:apollo-gradle-plugin:${version}"
            const val cache = "com.apollographql.apollo3:apollo-normalized-cache:${version}"
            const val cache_sqlite = "com.apollographql.apollo3:apollo-normalized-cache-sqlite:${version}"
        }

    }

    object AndroidX {
       const val material = "com.google.android.material:material:1.6.1"
       object Core{
           const val core = "androidx.core:core-ktx:1.7.0"
           const val compat = "androidx.appcompat:appcompat:1.4.2"
           private const val arch_version = "2.1.0"
           const val testImplementation = "androidx.arch.core:core-testing:$arch_version"
           private const val startup_version = "1.1.1"
           const val start_runtime = "androidx.startup:startup-runtime:$startup_version"
           const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0"
           private const val worker_version = "2.7.1"
           const val   worker_runtime  =  "androidx.work:work-runtime:$worker_version"
           const val worker_runtime_ktx = "androidx.work:work-runtime-ktx:$worker_version"

           const val palette = "com.android.support:palette-v7:28.0.0"
       }
        object Camera{
            private const val version = "1.1.0"
            const val core = "androidx.camera:camera-core:$version"
            const val camera2 = "androidx.camera:camera-camera2:$version"
            const val lifecycle = "androidx.camera:camera-lifecycle:$version"
            const val view = "androidx.camera:camera-view:$version"
        }


        object Room{
            private const val version = "2.4.3"
            const val runtime = "androidx.room:room-runtime:$version"
            const val kapt_compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"

        }
        object DataStore{
            private const val version = "1.0.0"
            const val core = "androidx.datastore:datastore-preferences-core:$version"
            const val preferences = "androidx.datastore:datastore-preferences:$version"
            const val typed = "androidx.datastore:datastore:$version"
        }

        object Paging{
            private const val version = "3.0.0-rc01"
            const val runtime = "androidx.paging:paging-runtime:$version"

        }

        object Test {
            private const val version = "1.4.0"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

        }

        object Compose {
            const val version = "1.2.1"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${version}"
            const val activity = "androidx.activity:activity-compose:1.3.1"
            const val junit = "androidx.compose.ui:ui-test-junit4:${version}"
            const val ui_tooling ="androidx.compose.ui:ui-tooling:${version}"
            const val ui_test = "androidx.compose.ui:ui-test-manifest:${version}"

            const val compiler = "androidx.compose.compiler:compiler:${Versions.Compose.version}"
            const val view_binding = "androidx.compose.ui:ui-viewbinding:${version}"
            const val runtime = "androidx.compose.runtime:runtime:${version}"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${version}"
            const val material_icons = "androidx.compose.material:material-icons-extended:${version}"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val animation = "androidx.compose.animation:animation:${version}"


            private const val nav_version = "2.4.0-alpha04"
            const val navigation = "androidx.navigation:navigation-compose:$nav_version"
            const val paging = "androidx.paging:paging-compose:1.0.0-alpha12"
        }
        object Navigation{

            private const val version = "2.5.1"
            const val compose = "androidx.navigation:navigation-compose:$version"

        }
        object Lifecycle {
            const val version = "2.5.1"
            const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewmodel_lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelSavedState =  "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"

        }
        object Fragment {
            const val version = "1.5.1"
            const val fragment_ktx = "androidx.fragment:fragment-ktx:$version"
            const val fragment = "androidx.fragment:fragment:$version"
            const val testing = "androidx.fragment:fragment-testing:$version"
        }
    }
    object Hilt {
        private const val version = "2.42"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val testing = "com.google.dagger:hilt-android-testing:$version"
        object AndroidX {
            private const val version = "1.0.0"
            const val compose_navigation = "androidx.hilt:hilt-navigation-compose:$version"
            const val worker = "androidx.hilt:hilt-work:$version"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
            const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        }
    }
    object Layout{
        private const val version = "2.1.4"
        const val  constraint = "androidx.constraintlayout:constraintlayout:$version"


    }
    object AssistedInjection {
        private const val version = "0.5.2"

        const val dagger = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processor = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }
    object Koin{
        const val version = "3.2.0"
        const val android = "io.insert-koin:koin-android:$version"
        const val koin = "io.insert-koin:koin-core:$version"
        const val test = "io.insert-koin:koin-test:$version"
        const val junit4 = "io.insert-koin:koin-test-junit4:$version"
        const val ktor = "io.insert-koin:koin-ktor:$version"
        const val slfj4 = "io.insert-koin:koin-logger-slf4j:$version"
        const val work_manager = "io.insert-koin:koin-androidx-workmanager:$version"
        const val navigation = "io.insert-koin:koin-androidx-navigation:$version"
        const val compose = "io.insert-koin:koin-androidx-compose"
    }

}