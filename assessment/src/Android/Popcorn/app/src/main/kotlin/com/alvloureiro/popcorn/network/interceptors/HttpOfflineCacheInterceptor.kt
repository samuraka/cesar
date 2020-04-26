package com.alvloureiro.popcorn.network.interceptors

import com.alvloureiro.popcorn.PopcornApplication
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HttpOfflineCacheInterceptor(val app: PopcornApplication): Interceptor {
    private companion object {
        const val MAX_STALE = 7
    }
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        if (!app.networkState.isNetworkAvailable()) {
            val cacheControl = CacheControl.Builder().maxStale(MAX_STALE, TimeUnit.DAYS).build()
            request = request?.newBuilder()?.cacheControl(cacheControl)?.build()
        }

        return chain?.proceed(request)!!
    }
}
