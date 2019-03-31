package br.com.azulpay.data.remote.datasource

import br.com.azulpay.data.remote.model.BaseResponseRemoteModel
import br.com.azulpay.data.remote.model.UserRemoteModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRemoteDataSource {
    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Single<BaseResponseRemoteModel<UserRemoteModel>>
}