package br.com.azulpay.data.remote.datasource

import br.com.azulpay.data.remote.model.LoginRequestRemoteModel
import br.com.azulpay.data.remote.model.LoginResponseRemoteModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRemoteDataSource {
    @POST("verifyPassword?key=AIzaSyBAzq8TMX9t8zkEwSX0WrjmewooC9TUv3I")
    fun login(@Body loginRequestRemoteModel: LoginRequestRemoteModel): Single<LoginResponseRemoteModel>
}