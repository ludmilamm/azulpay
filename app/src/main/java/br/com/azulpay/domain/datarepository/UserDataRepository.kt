package br.com.azulpay.domain.datarepository

import br.com.azulpay.domain.model.User
import io.reactivex.Single

interface UserDataRepository {
    fun getAuthenticatedUser(): Single<User>
    fun getUsers(): Single<List<User>>
}