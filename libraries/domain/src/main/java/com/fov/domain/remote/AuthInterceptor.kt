package com.fov.domain.remote

import okhttp3.Interceptor

class AuthInterceptor(private val tokenType: String, private val acceessToken: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        var request = chain.request()
        if (Constants.excludeOnAuth.contains(request.url.encodedPath)) {
            return  chain.proceed(request);
        }
        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

        return chain.proceed(request)
    }
}