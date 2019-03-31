package br.com.azulpay.data.remote.infrastructure

import br.com.azulpay.data.cache.datasource.UserCacheDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GeneralInterceptor @Inject constructor(private val userCacheDataSource: UserCacheDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = userCacheDataSource.getUserToken().onErrorReturnItem("").blockingGet()

        val newRequest = originalRequest.newBuilder()
            .apply {
                if (token.isNotEmpty())
                    addHeader("Authorization", "Bearer $token")
            }.build()

        return chain.proceed(newRequest)
    }
}