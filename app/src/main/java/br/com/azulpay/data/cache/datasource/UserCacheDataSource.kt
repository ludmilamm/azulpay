package br.com.azulpay.data.cache.datasource

import br.com.azulpay.data.cache.model.UserCacheModel
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCacheDataSource @Inject constructor() {

    private val cacheKey = "user"

    fun insertUser(user: UserCacheModel): Completable = RxPaperBook.with().write(cacheKey, user)

    fun getUser(): Single<UserCacheModel> = RxPaperBook.with().read(cacheKey)

    fun getUserToken(): Single<String> = getUser().map { it.token }

    fun deleteUser(): Completable {
        return RxPaperBook.with().delete(cacheKey)
    }
}