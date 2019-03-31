package br.com.azulpay.data.repository

import br.com.azulpay.data.cache.datasource.UserCacheDataSource
import br.com.azulpay.data.cache.model.UserCacheModel
import br.com.azulpay.data.cache.toDomainModel
import br.com.azulpay.data.remote.datasource.LoginRemoteDataSource
import br.com.azulpay.data.remote.datasource.UserRemoteDataSource
import br.com.azulpay.data.remote.model.LoginRequestRemoteModel
import br.com.azulpay.data.remote.toCacheModel
import br.com.azulpay.domain.datarepository.UserDataRepository
import br.com.azulpay.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val loginRemoteDataSource: LoginRemoteDataSource,
                                         private val userRemoteDataSource: UserRemoteDataSource,
                                         private val userCacheDataSource: UserCacheDataSource) : UserDataRepository {

    private fun login(): Single<UserCacheModel> {
        return loginRemoteDataSource.login(LoginRequestRemoteModel())
                .flatMap { userRemoteDataSource.getUser(it.id).map { user -> user.response.toCacheModel(it.token, it.refreshToken) } }
                .flatMap { userCacheDataSource.insertUser(it).toSingleDefault(it) }
    }

    override fun getAuthenticatedUser(): Single<User> {
        return userCacheDataSource.getUser().onErrorResumeNext { login() }.map { it.toDomainModel() }
    }
}