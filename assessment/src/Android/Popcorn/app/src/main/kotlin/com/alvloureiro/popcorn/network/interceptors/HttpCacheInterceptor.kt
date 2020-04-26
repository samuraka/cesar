package com.alvloureiro.popcorn.network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit


class HttpCacheInterceptor: Interceptor {
    companion object {
        const val CACHE_CONTROL = "Cache-Control"
        const val MAX_AGE = 3
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val response = chain?.proceed(chain.request())
        val cacheControl = CacheControl.Builder().maxAge(MAX_AGE, TimeUnit.MINUTES).build()

        return response?.newBuilder()?.header(CACHE_CONTROL, cacheControl.toString())?.build()!!
    }
}
