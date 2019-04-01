package br.com.azulpay.data.remote.infrastructure

import br.com.azulpay.data.cache.datasource.UserCacheDataSource
import br.com.azulpay.data.remote.datasource.LoginRemoteDataSource
import br.com.azulpay.data.remote.model.RefreshTokenRequestRemoteModel
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(private val userCacheDataSource: UserCacheDataSource,
                                             private val loginRemoteDataSource: LoginRemoteDataSource) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val newToken = userCacheDataSource.getUser()
                .flatMap { loginRemoteDataSource.refreshToken(RefreshTokenRequestRemoteModel(it.refreshToken)) }
                .map { it.token }
                .onErrorReturnItem("")
                .blockingGet()

        return response.request().newBuilder().addHeader("Authorization", "Bearer $newToken").build()
    }
}