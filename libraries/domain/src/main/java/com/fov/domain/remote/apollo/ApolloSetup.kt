package com.fov.domain.remote.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
//import com.fov.domain.BuildConfig
import com.fov.domain.remote.mock.MockInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloSetup {
    fun setUpApolloClient(url: String, token: String = "", subscriptionUrl : String = ""): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(MockInterceptor())
        if (token.isNotEmpty()) {
            okHttp.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header("Authorization", "Bearer $token")
                chain.proceed(builder.build())
            }
        }



        var builder = ApolloClient.builder()
             .serverUrl("https://{BuildConfig.fovMusicApiUrl}${url}")
             .okHttpClient(okHttp.build())
        if(subscriptionUrl.isNotEmpty()){
            val subscriptionTransportFactory = WebSocketSubscriptionTransport
                .Factory(subscriptionUrl, okHttp.build())
            builder
                //.subscriptionConnectionParams(HeadersProvider.HEADERS)
                .subscriptionTransportFactory(subscriptionTransportFactory)
        }

            return builder.build();
    }
    fun setUpTestApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(MockInterceptor())


        return ApolloClient.builder()
            .serverUrl("https://graphqlzero.almansi.me/api")
            .okHttpClient(okHttp.build())
            .build();
    }
}